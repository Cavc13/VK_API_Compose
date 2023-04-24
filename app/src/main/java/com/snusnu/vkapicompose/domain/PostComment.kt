package com.snusnu.vkapicompose.domain

import com.snusnu.vkapicompose.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.comment_author_avatar,
    val comment: String = "Long comment text",
    val publicationDate: String = "14:00"
) {
}