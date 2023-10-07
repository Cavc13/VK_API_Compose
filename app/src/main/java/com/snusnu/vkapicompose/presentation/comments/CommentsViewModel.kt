package com.snusnu.vkapicompose.presentation.comments

import androidx.lifecycle.ViewModel
import com.snusnu.vkapicompose.domain.entity.FeedPost
import com.snusnu.vkapicompose.domain.usecases.GetWallCommentsUseCase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val getWallCommentsUseCase: GetWallCommentsUseCase,
    private val feedPost: FeedPost
): ViewModel() {

    val screenState = getWallCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }
}