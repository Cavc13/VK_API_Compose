package com.snusnu.vkapicompose.ui.theme.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

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
    var fabIsVisible: Boolean by remember {
        mutableStateOf(true)
    }

    if (fabIsVisible) {
        FloatingActionButton(
            onClick = {
                scope.launch {
                    val action  = snackBarHostState.showSnackbar(
                        message = "This is snackBar",
                        actionLabel = "Hide FAB",
                        duration = SnackbarDuration.Long
                    )
                    if (action == SnackbarResult.ActionPerformed) {
                        fabIsVisible = false
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