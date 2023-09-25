package com.snusnu.vkapicompose.presentation.comments

import com.snusnu.vkapicompose.domain.FeedPost
import com.snusnu.vkapicompose.domain.PostComment

sealed class CommentsScreenState {

    object Initial: CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ): CommentsScreenState()
}