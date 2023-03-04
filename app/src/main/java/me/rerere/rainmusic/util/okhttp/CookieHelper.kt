package me.rerere.rainmusic.util.okhttp

import android.util.Log
import androidx.core.content.edit
import me.rerere.rainmusic.util.sharedPreferencesOf
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

private const val TAG = "CookieHelper"

object CookieHelper : CookieJar {
    fun logout(){
        sharedPreferencesOf("cookie").edit {
            clear()
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies = sharedPreferencesOf("cookie").all.map { (k, v) ->
            Cookie.Builder()
                .domain("music.163.com")
                .name(k)
                .value(v.toString())
                .build()
        }.toMutableList()
        if (!cookies.any {
                it.name == "os"
            }) {
            cookies += Cookie.Builder()
                .domain("music.163.com")
                .name("os")
                .value("pc")
                .build()
        }
        if (!cookies.any {
                it.name == "appver"
            }) {
            cookies += Cookie.Builder()
                .domain("music.163.com")
                .name("appver")
                .value("2.7.1.198277")
                .build()
        }
        return cookies.also {
            Log.d(TAG, "loadForRequest: ${cookies.joinToString(separator = ","){it.name}}")
        }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        sharedPreferencesOf("cookie").let {
            cookies
                .filter {
                    it.domain == "music.163.com"
                }
                .forEach { cookie ->
                    it.edit {
                        putString(cookie.name, cookie.value)
                        Log.i(
                            TAG,
                            "saveFromResponse: saved cookie: ${cookie.name}=${cookie.value}"
                        )
                    }
                }
        }
    }
}