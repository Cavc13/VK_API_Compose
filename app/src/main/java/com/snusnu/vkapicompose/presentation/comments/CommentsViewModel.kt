package com.snusnu.vkapicompose.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.snusnu.vkapicompose.data.repository.NewsFeedRepositoryImpl
import com.snusnu.vkapicompose.domain.entity.FeedPost
import com.snusnu.vkapicompose.domain.usecases.GetWallCommentsUseCase
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    application: Application,
    feedPost: FeedPost
): AndroidViewModel(application) {
    private val repository = NewsFeedRepositoryImpl(application)
    private val getWallCommentsUseCase = GetWallCommentsUseCase(repository)

    val screenState = getWallCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}