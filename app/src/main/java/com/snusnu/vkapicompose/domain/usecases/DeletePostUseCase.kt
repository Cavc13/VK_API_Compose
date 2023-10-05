package com.snusnu.vkapicompose.domain.usecases

import com.snusnu.vkapicompose.domain.entity.FeedPost
import com.snusnu.vkapicompose.domain.repository.NewsFeedRepository

class DeletePostUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) {
        return repository.deletePost(feedPost)
    }
}