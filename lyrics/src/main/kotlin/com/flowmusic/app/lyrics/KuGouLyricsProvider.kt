package com.flowmusic.app.lyrics

import android.content.Context
import com.music.kugou.KuGou
import com.flowmusic.app.constants.EnableKugouKey
import com.flowmusic.app.utils.dataStore
import com.flowmusic.app.utils.get

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
