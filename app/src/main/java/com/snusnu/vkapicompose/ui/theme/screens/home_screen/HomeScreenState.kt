package com.snusnu.vkapicompose.ui.theme.screens.home_screen

import com.snusnu.vkapicompose.domain.FeedPost
import com.snusnu.vkapicompose.domain.PostComment

sealed class HomeScreenState {

    object Initial: HomeScreenState()

    data class Posts(val posts: List<FeedPost>): HomeScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ): HomeScreenState()
}