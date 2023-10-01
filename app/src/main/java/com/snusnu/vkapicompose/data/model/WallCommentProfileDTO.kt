package com.snusnu.vkapicompose.data.model

import com.google.gson.annotations.SerializedName

data class WallCommentProfileDTO(
    @SerializedName("id") val idCommentProfile: Long,
    @SerializedName("photo_100") val photoCommentProfile: String,
    @SerializedName("first_name") val firstNameCommentProfile: String,
    @SerializedName("last_name") val lastNameCommentProfile: String,
)
