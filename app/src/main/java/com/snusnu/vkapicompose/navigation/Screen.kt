package com.snusnu.vkapicompose.navigation

sealed class Screen(
    val route: String
) {
    object Favorite: Screen(ROUTE_FAVORITE)
    object Profile: Screen(ROUTE_PROFILE)
    object NewsFeed: Screen(ROUTE_NEWS_FEED)
    object Home: Screen(ROUTE_HOME)
    object Comments: Screen(ROUTE_COMMENTS)

    private companion object {
        const val ROUTE_COMMENTS = "comments"
        const val ROUTE_HOME = "home"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"
    }
}