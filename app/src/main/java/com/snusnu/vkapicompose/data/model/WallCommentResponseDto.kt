package com.snusnu.vkapicompose.data.model

import com.google.gson.annotations.SerializedName

data class WallCommentResponseDto(
    @SerializedName("response") val wallComments: WallCommentsDTO
)
