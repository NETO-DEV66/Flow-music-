package com.tunevol.app.lyrics

import android.content.Context
import com.music.simpmusic.SimpMusicLyrics
import com.tunevol.app.constants.EnableSimpMusicKey
import com.tunevol.app.utils.dataStore
import com.tunevol.app.utils.get

object SimpMusicLyricsProvider : LyricsProvider {
  override val name = "SimpMusic"

  override fun isEnabled(context: Context): Boolean = context.dataStore[EnableSimpMusicKey] ?: true

  override suspend fun getLyrics(
    id: String,
    title: String,
    artist: String,
    duration: Int,
    album: String?,
  ): Result<String> = SimpMusicLyrics.getLyrics(id, duration)

  override suspend fun getAllLyrics(
    id: String,
    title: String,
    artist: String,
    duration: Int,
    album: String?,
    callback: (String) -> Unit,
  ) {
    SimpMusicLyrics.getAllLyrics(id, duration, callback)
  }
}
