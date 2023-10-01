package com.snusnu.vkapicompose.data.model

import com.google.gson.annotations.SerializedName

data class WallCommentContentDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val idOwnerComment: Long,
    @SerializedName("date") val dateComment: Long,
    @SerializedName("text") val textComment: String
)
