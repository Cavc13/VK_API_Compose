package com.snusnu.vkapicompose.ui.theme.screens.home_screen

import com.snusnu.vkapicompose.domain.FeedPostModel
import com.snusnu.vkapicompose.domain.PostComment

sealed class HomeScreenState {

    object Initial: HomeScreenState()

    data class Posts(val posts: List<FeedPostModel>): HomeScreenState()

    data class Comments(
        val feedPost: FeedPostModel,
        val comments: List<PostComment>
    ): HomeScreenState()
}