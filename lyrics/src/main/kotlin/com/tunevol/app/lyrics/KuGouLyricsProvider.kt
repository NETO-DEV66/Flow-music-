package com.tunevol.app.lyrics

import android.content.Context
import com.music.kugou.KuGou
import com.tunevol.app.constants.EnableKugouKey
import com.tunevol.app.utils.dataStore
import com.tunevol.app.utils.get

object KuGouLyricsProvider : LyricsProvider {
  override val name = "Kugou"

  override fun isEnabled(context: Context): Boolean = context.dataStore[EnableKugouKey] ?: true

  override suspend fun getLyrics(
    id: String,
    title: String,
    artist: String,
    duration: Int,
    album: String?,
  ): Result<String> = KuGou.getLyrics(title, artist, duration, album)

  override suspend fun getAllLyrics(
    id: String,
    title: String,
    artist: String,
    duration: Int,
    album: String?,
    callback: (String) -> Unit,
  ) {
    KuGou.getAllPossibleLyricsOptions(title, artist, duration, album, callback)
  }
}
