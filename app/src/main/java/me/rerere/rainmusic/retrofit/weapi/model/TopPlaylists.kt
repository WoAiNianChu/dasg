package me.rerere.rainmusic.retrofit.weapi.model

import com.google.gson.annotations.SerializedName
import me.rerere.rainmusic.model.Playlists

data class TopPlaylists(
    @SerializedName("cat")
    val cat: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("more")
    val more: Boolean,
    @SerializedName("playlists")
    val playlists: List<Playlists>?,
    @SerializedName("total")
    val total: Int
)