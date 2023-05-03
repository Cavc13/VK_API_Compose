package com.snusnu.vkapicompose.ui.theme.screens.home_screen

import com.snusnu.vkapicompose.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial: NewsFeedScreenState()

    data class Posts(val posts: List<FeedPost>): NewsFeedScreenState()
}