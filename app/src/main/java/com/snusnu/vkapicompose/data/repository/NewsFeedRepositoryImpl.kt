package com.snusnu.vkapicompose.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.snusnu.vkapicompose.data.mapper.NewsFeedMapper
import com.snusnu.vkapicompose.data.network.ApiService
import com.snusnu.vkapicompose.domain.entity.FeedPost
import com.snusnu.vkapicompose.domain.entity.PostComment
import com.snusnu.vkapicompose.domain.entity.StatisticItem
import com.snusnu.vkapicompose.domain.entity.StatisticType
import com.snusnu.vkapicompose.extentions.mergeWith
import com.snusnu.vkapicompose.domain.entity.AuthState
import com.snusnu.vkapicompose.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import java.lang.IllegalStateException
import javax.inject.Inject

class NewsFeedRepositoryImpl  @Inject constructor(
    private val storage: VKPreferencesKeyValueStorage,
    private val apiService: ApiService,
    private val mapper: NewsFeedMapper
): NewsFeedRepository {

    private val token
        get() = VKAccessToken.restore(storage)

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()
    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.loadRecommendations(getAccessToken())
            } else {
                apiService.loadRecommendations(getAccessToken(), startFrom)
            }
            nextFrom = response.newsFeedContentDto.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry(2) {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }.catch {

    }

    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = token
            val isLogin = currentToken != null && currentToken.isValid
            val authState= if (isLogin) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        AuthState.Initial
    )

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override fun getAuthstateFlow(): StateFlow<AuthState> = authStateFlow

    override fun getRecommendationsFlow(): StateFlow<List<FeedPost>> = recommendations

    override fun getWallComments(feedPost: FeedPost): StateFlow<List<PostComment>> = flow {
        val response = apiService.getWallComments(
            getAccessToken(),
            feedPost.communityId,
            feedPost.id
        )

        emit(mapper.mapWallCommentsResponseToPostComments(response.wallComments))
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }.stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        listOf()
    )

    private val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            coroutineScope,
            SharingStarted.Lazily,
            feedPosts
        )

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }


    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignoreItem(
            getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshedListFlow.emit(feedPosts)
    }

    companion object{
        private const val RETRY_TIMEOUT_MILLIS = 3_000L
    }
}