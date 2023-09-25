package com.snusnu.vkapicompose.presentation.news

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snusnu.vkapicompose.domain.FeedPost

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit
) {
    val newsFeedViewModel: NewsFeedViewModel = viewModel()
    val screenState = newsFeedViewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    when(val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts,
                newsFeedViewModel = newsFeedViewModel,
                paddingValues = paddingValues,
                onCommentsClickListener = onCommentsClickListener
            )
        }
        NewsFeedScreenState.Initial -> {}
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    posts: List<FeedPost>,
    newsFeedViewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit
) {
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
        items(
            items = posts,
            key = { it.id }
        ) { feedPost ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                newsFeedViewModel.deleteFeedPost(feedPost)
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
                    feedPost = feedPost,
                    onViewItemClickListener = {
                        newsFeedViewModel.increaseCount(feedPost, it)
                    },
                    onShareItemClickListener = {
                        newsFeedViewModel.increaseCount(feedPost, it)
                    },
                    onCommentItemClickListener = {
                        onCommentsClickListener(feedPost)
                    },
                    onLikeItemClickListener = {
                        newsFeedViewModel.changeLikeStatus(feedPost)
                    },
                )
            }
        }
    }
}