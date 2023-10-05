package com.snusnu.vkapicompose.domain

sealed class AuthState {
    object Authorized: AuthState()
    object NotAuthorized: AuthState()
    object Initial: AuthState()
}

