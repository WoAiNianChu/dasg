package me.rerere.rainmusic.retrofit.weapi.model


import com.google.gson.annotations.SerializedName

data class LikeList(
    @SerializedName("ids")
    val ids: List<Long>
)