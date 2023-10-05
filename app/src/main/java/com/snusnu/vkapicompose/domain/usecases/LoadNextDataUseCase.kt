package com.snusnu.vkapicompose.domain.usecases

import com.snusnu.vkapicompose.domain.repository.NewsFeedRepository

class LoadNextDataUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke() {
        return repository.loadNextData()
    }
}