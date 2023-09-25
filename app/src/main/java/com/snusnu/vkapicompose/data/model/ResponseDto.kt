package com.snusnu.vkapicompose.data.model

import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("response") val newsFeedContentDto: NewsFeedContentDto
)
