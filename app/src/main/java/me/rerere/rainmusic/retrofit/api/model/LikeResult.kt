package me.rerere.rainmusic.retrofit.api.model

import com.google.gson.annotations.SerializedName

data class LikeResult(
    @SerializedName("code")
    val code: Int
)