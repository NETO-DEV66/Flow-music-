package com.flowmusic.app.lyrics

import android.content.Context
import com.flowmusic.app.betterlyrics.BetterLyrics
import com.flowmusic.app.constants.EnableBetterLyricsKey
import com.flowmusic.app.utils.dataStore
import com.flowmusic.app.utils.get

object BetterLyricsProvider : LyricsProvider {
  override val name = "BetterLyrics"

  override fun isEnabled(context: Context): Boolean =
    context.dataStore[EnableBetterLyricsKey] ?: true

  override suspend fun getLyrics(
    id: String,
    title: String,
    artist: String,
    duration: Int,
    album: String?,
  ): Result<String> = BetterLyrics.getLyrics(title, artist, duration, album)

  override suspend fun getAllLyrics(
    id: String,
    title: String,
    artist: String,
    duration: Int,
    album: String?,
    callback: (String) -> Unit,
  ) {
    BetterLyrics.getAllLyrics(title, artist, duration, album, callback)
  }
}
