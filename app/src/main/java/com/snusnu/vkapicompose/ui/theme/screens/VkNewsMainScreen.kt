@file:OptIn(ExperimentalMaterialApi::class)

package com.snusnu.vkapicompose.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snusnu.vkapicompose.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    Scaffold(
        bottomBar = {
            MainBottomNavigation()
        }
    ) { paddingValues ->
        val feedPostModels = mainViewModel.feedPostModels.observeAsState(emptyList())
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 72.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(feedPostModels.value, key = { it.id }) { feedPostModel ->
                val dismissState = rememberDismissState()
                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    mainViewModel.deleteFeedPost(feedPostModel)
                }
                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .background(Color.Red.copy(alpha = 0.5f))
                                .fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Delete post",
                                color = Color.White,
                                fontSize = 30.sp
                            )
                        }
                    }
                ) {
                    CardPost(
                        feedPostModel = feedPostModel,
                        onViewItemClickListener = {
                            mainViewModel.increaseCount(feedPostModel, it)
                        },
                        onShareItemClickListener = {
                            mainViewModel.increaseCount(feedPostModel, it)
                        },
                        onCommentItemClickListener = {
                            mainViewModel.increaseCount(feedPostModel, it)
                        },
                        onLikeItemClickListener = {
                            mainViewModel.increaseCount(feedPostModel, it)
                        },
                    )
                }
            }
        }

    }
}

@Composable
private fun MainBottomNavigation() {
    BottomNavigation() {
        val selectedItemPosition = remember {
            mutableStateOf(0)
        }
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Favourite,
            NavigationItem.Profile
        )

        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = selectedItemPosition.value == index,
                onClick = { selectedItemPosition.value = index },
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