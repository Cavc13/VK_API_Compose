package com.snusnu.vkapicompose.ui.theme.screens.home_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snusnu.vkapicompose.MainViewModel
import com.snusnu.vkapicompose.domain.FeedPostModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val screenState = mainViewModel.screenState.observeAsState(HomeScreenState.Initial)

    when(val currentState = screenState.value) {
        is HomeScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts,
                mainViewModel = mainViewModel,
                paddingValues = paddingValues
            )
        }
        is HomeScreenState.Comments -> {
            CommentScreen(feedPost = currentState.feedPost, comments = currentState.comments)
        }
        HomeScreenState.Initial -> {}
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    posts: List<FeedPostModel>,
    mainViewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = posts, key = { it.id }) { feedPostModel ->
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