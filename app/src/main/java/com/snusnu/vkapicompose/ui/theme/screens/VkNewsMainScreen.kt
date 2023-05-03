@file:OptIn(ExperimentalMaterialApi::class)

package com.snusnu.vkapicompose.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.snusnu.vkapicompose.domain.FeedPost
import com.snusnu.vkapicompose.navigation.AppNavGraph
import com.snusnu.vkapicompose.navigation.rememberNavigationState
import com.snusnu.vkapicompose.ui.theme.screens.home_screen.CommentScreen
import com.snusnu.vkapicompose.ui.theme.screens.home_screen.HomeScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()
    val commentsToPost: MutableState<FeedPost?> = remember {
        mutableStateOf(null)
    }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navigationState
                    .navHostController
                    .currentBackStackEntryAsState()
                val currentRout = navBackStackEntry?.destination?.route
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )

                items.forEach { item ->
                    BottomNavigationItem(
                        selected = currentRout == item.screen.route,
                        onClick = {
                            navigationState.navigateTo(item.screen.route)
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = {
                if (commentsToPost.value == null) {
                    HomeScreen(
                        paddingValues = paddingValues,
                        onCommentsClickListener = {
                            commentsToPost.value = it
                        }
                    )
                } else {
                    CommentScreen(
                        onBackPressed = {
                            commentsToPost.value = null
                        },
                        feedPost = commentsToPost.value!!
                    )
                }
            },
            favoriteScreenContent = {
                NumberCounter("Favourite")
            },
            profileScreenContent = {
                NumberCounter("Profile")
            }
        )
    }
}

@Composable
private fun NumberCounter(name: String) {
    var count by rememberSaveable {
        mutableStateOf(0)
    }

    Text(
        modifier = Modifier.clickable { count++ },
        text = "$name Count: $count"
    )
}