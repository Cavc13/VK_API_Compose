package com.snusnu.vkapicompose.ui.theme.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.snusnu.vkapicompose.ui.domain.FeedPost

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val feedPost = remember {
        mutableStateOf(FeedPost())
    }

    Scaffold(
        bottomBar = {
            MainBottomNavigation()
        }
    ) {
        CardPost(
            modifier = Modifier.padding(8.dp),
            feedPost = feedPost.value,
            onStatisticItemClickListener = { newItem ->
                val oldStatistics = feedPost.value.statistics
                val newStatistics = oldStatistics.toMutableList().apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        replaceAll { oldItem ->
                            if (oldItem.type == newItem.type) {
                                oldItem.copy(count = oldItem.count + 1)
                            } else {
                                oldItem
                            }
                        }
                    }
                }
                feedPost.value = feedPost.value.copy(statistics = newStatistics)
            }
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