package com.snusnu.vkapicompose.domain

data class PostComment(
    val id: Long,
    val authorName: String,
    val authorAvatarUrl: String,
    val comment: String,
    val publicationDate: String
)