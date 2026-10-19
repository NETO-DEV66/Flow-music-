package com.flowtune.app.playback

import com.flowtune.app.models.MediaMetadata

data class LyricsWithProvider(val lyrics: String?, val providerName: String)

interface ILyricsHelper {
  suspend fun getLyrics(mediaMetadata: MediaMetadata): LyricsWithProvider
}
