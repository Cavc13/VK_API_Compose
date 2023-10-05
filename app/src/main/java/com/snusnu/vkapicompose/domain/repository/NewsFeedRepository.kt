package com.snusnu.vkapicompose.domain.repository

import com.snusnu.vkapicompose.domain.entity.FeedPost
import com.snusnu.vkapicompose.domain.entity.PostComment
import com.snusnu.vkapicompose.domain.entity.AuthState
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getAuthstateFlow(): StateFlow<AuthState>

    fun getRecommendationsFlow(): StateFlow<List<FeedPost>>

    fun getWallComments(feedPost: FeedPost): StateFlow<List<PostComment>>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)
}