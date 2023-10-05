package com.snusnu.vkapicompose.domain.usecases

import com.snusnu.vkapicompose.domain.entity.FeedPost
import com.snusnu.vkapicompose.domain.entity.PostComment
import com.snusnu.vkapicompose.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetWallCommentsUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> {
        return repository.getWallComments(feedPost)
    }
}