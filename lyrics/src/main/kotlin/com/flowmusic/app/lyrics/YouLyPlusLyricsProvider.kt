package com.flowmusic.app.lyrics

import android.content.Context
import com.music.youlyplus.YouLyPlus
import com.flowmusic.app.constants.EnableYouLyPlusKey
import com.flowmusic.app.utils.dataStore
import com.flowmusic.app.utils.get

object YouLyPlusLyricsProvider : LyricsProvider {
  override val name = "YouLyPlus"

  override fun isEnabled(context: Context): Boolean = context.dataStore[EnableYouLyPlusKey] ?: true

  override suspend fun getLyrics(
    id: String,
    title: String,
    artist: String,
    duration: Int,
    album: String?,
  ): Result<String> = YouLyPlus.getLyrics(title, artist, duration, album, id)

  override suspend fun getAllLyrics(
    id: String,
    title: String,
    artist: String,
    duration: Int,
    album: String?,
    callback: (String) -> Unit,
  ) {
    YouLyPlus.getAllLyrics(title, artist, duration, album, id, null, callback)
  }
}
