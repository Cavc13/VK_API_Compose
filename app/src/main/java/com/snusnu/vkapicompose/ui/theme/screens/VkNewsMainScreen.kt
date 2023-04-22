package com.snusnu.vkapicompose.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.snusnu.vkapicompose.MainViewModel
import com.snusnu.vkapicompose.domain.FeedPost

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {

    Scaffold(
        bottomBar = {
            MainBottomNavigation()
        }
    ) {
        val feedPost = mainViewModel.feedPost.observeAsState(FeedPost())

        CardPost(
            modifier = Modifier.padding(8.dp),
            feedPost = feedPost.value,
            onViewItemClickListener = mainViewModel::increaseCount,
            onShareItemClickListener = mainViewModel::increaseCount,
            onCommentItemClickListener = mainViewModel::increaseCount,
            onLikeItemClickListener = mainViewModel::increaseCount
        )
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