package com.snusnu.vkapicompose.data.model

import com.google.gson.annotations.SerializedName

data class WallCommentsDTO(
    @SerializedName("items") val wallComments: List<WallCommentContentDTO>,
    @SerializedName("profiles") val wallCommentProfiles: List<WallCommentProfileDTO>
)
