package com.flowtune.app.playback

import com.flowtune.app.db.entities.SongEntity

interface ISyncUtils {
  fun likeSong(song: SongEntity)
}
