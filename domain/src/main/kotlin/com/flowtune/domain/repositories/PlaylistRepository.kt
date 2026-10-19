package com.flowtune.domain.repositories

import com.flowtune.domain.models.Playlist
import com.flowtune.domain.models.Song
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
  fun getAllPlaylists(): Flow<List<Playlist>>
  fun getPlaylistSongs(playlistId: String): Flow<List<Song>>
  suspend fun addSongToPlaylist(playlistId: String, songId: String)
  suspend fun removeSongFromPlaylist(playlistId: String, songId: String)
}
