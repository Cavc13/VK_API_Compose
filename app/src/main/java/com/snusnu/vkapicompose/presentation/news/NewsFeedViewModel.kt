package com.snusnu.vkapicompose.presentation.news

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.snusnu.vkapicompose.data.repository.NewsFeedRepositoryImpl
import com.snusnu.vkapicompose.domain.entity.FeedPost
import com.snusnu.vkapicompose.domain.usecases.ChangeLikeStatusUseCase
import com.snusnu.vkapicompose.domain.usecases.DeletePostUseCase
import com.snusnu.vkapicompose.domain.usecases.GetRecommendationsUseCase
import com.snusnu.vkapicompose.domain.usecases.LoadNextDataUseCase
import com.snusnu.vkapicompose.extentions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("exceptionHandler", "Иссключение поймано exceptionHandler")
    }

    private val repository = NewsFeedRepositoryImpl(application)
    private val getRecommendationsUseCase = GetRecommendationsUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val deletePostUseCase = DeletePostUseCase(repository)

    private val recommendationsFlow = getRecommendationsUseCase()
    private val loadNextDataFlow = MutableSharedFlow<NewsFeedScreenState>()

    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map {
            val result: NewsFeedScreenState  = NewsFeedScreenState.Posts(it)
            result
        }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsFeedScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataIsLoading = true
                )
            )
            loadNextDataUseCase()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }

    fun deleteFeedPost(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost)
        }

    }
}