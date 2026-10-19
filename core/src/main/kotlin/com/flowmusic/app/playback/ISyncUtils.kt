package com.flowmusic.app.playback

import com.flowmusic.app.db.entities.SongEntity

interface ISyncUtils {
  fun likeSong(song: SongEntity)
}
