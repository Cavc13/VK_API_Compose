package com.snusnu.vkapicompose.ui.theme.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val snackBarHostState = SnackbarHostState()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButton = {
            MainFloatingActionButton(snackBarHostState)
        },
        bottomBar = {
            MainBottomNavigation()
        }
    ) {

    }
}

@Composable
private fun MainFloatingActionButton(snackBarHostState: SnackbarHostState) {
    val scope = rememberCoroutineScope()
    val fabIsVisible = remember {
        mutableStateOf(true)
    }

    if (fabIsVisible.value) {
        FloatingActionButton(
            onClick = {
                scope.launch {
                    val action  = snackBarHostState.showSnackbar(
                        message = "This is snackBar",
                        actionLabel = "Hide FAB",
                        duration = SnackbarDuration.Long
                    )
                    if (action == SnackbarResult.ActionPerformed) {
                        fabIsVisible.value = false
                    }
                }
            }
        ) {
            Icon(Icons.Filled.Favorite, contentDescription = null)
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