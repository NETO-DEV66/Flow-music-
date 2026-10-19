package com.flowtune.app.musicflow

import androidx.datastore.preferences.core.Preferences
import com.flowtune.app.constants.CanvasThumbnailAnimationKey
import com.flowtune.app.constants.DataSaverEnabledKey
import com.flowtune.app.constants.MusicFlowAnimatedBackgroundKey
import com.flowtune.app.constants.MusicFlowAutoNextKey
import com.flowtune.app.constants.MusicFlowAutoplayKey
import com.flowtune.app.constants.MusicFlowCellularPrefetchKey
import com.flowtune.app.constants.MusicFlowEnabledKey
import com.flowtune.app.constants.MusicFlowHideRecentKey
import com.flowtune.app.constants.MusicFlowLyricsKey

data class MusicFlowPreferences(
  val enabled: Boolean = true,
  val autoplay: Boolean = true,
  val autoNext: Boolean = true,
  val animatedBackground: Boolean = false,
  val lyrics: Boolean = true,
  val cellularPrefetch: Boolean = true,
  val hideRecent: Boolean = true,
  val dataSaver: Boolean = false,
) {
  companion object {
    fun from(preferences: Preferences) =
      MusicFlowPreferences(
        enabled = preferences[MusicFlowEnabledKey] ?: true,
        autoplay = preferences[MusicFlowAutoplayKey] ?: true,
        autoNext = preferences[MusicFlowAutoNextKey] ?: true,
        animatedBackground =
          preferences[MusicFlowAnimatedBackgroundKey]
            ?: preferences[CanvasThumbnailAnimationKey]
            ?: false,
        lyrics = preferences[MusicFlowLyricsKey] ?: true,
        cellularPrefetch = preferences[MusicFlowCellularPrefetchKey] ?: true,
        hideRecent = preferences[MusicFlowHideRecentKey] ?: true,
        dataSaver = preferences[DataSaverEnabledKey] ?: false,
      )
  }
}
