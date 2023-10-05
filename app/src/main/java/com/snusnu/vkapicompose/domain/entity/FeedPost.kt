package com.snusnu.vkapicompose.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class FeedPost(
    val id: Long,
    val communityId: Long,
    val communityName: String,
    val publicationDate: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticItem>,
    val isLiked: Boolean
): Parcelable