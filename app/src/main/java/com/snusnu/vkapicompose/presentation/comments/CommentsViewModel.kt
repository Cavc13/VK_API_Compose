package com.snusnu.vkapicompose.presentation.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.snusnu.vkapicompose.data.repository.NewsFeedRepository
import com.snusnu.vkapicompose.domain.FeedPost
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    application: Application,
    feedPost: FeedPost
): AndroidViewModel(application) {
    private val repository = NewsFeedRepository(application)

    val screenState = repository.getWallComments(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}