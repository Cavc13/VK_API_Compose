package com.snusnu.vkapicompose.domain.usecases

import com.snusnu.vkapicompose.domain.entity.AuthState
import com.snusnu.vkapicompose.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthstateFlow()
    }
}