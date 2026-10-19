package com.tunevol.app.playback

import com.tunevol.app.db.entities.SongEntity

interface ISyncUtils {
  fun likeSong(song: SongEntity)
}
