package com.flowmusic.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.flowmusic.core.repository.PlaylistRepositoryImpl
import com.flowmusic.core.repository.SongRepositoryImpl
import com.flowmusic.domain.repositories.PlaylistRepository
import com.flowmusic.domain.repositories.SongRepository
import com.flowmusic.app.musicflow.MusicFlowFeedSource
import com.flowmusic.app.musicflow.MusicFlowRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainBindingsModule {

  @Binds
  @Singleton
  abstract fun bindSongRepository(
    impl: SongRepositoryImpl
  ): SongRepository

  @Binds
  @Singleton
  abstract fun bindPlaylistRepository(
    impl: PlaylistRepositoryImpl
  ): PlaylistRepository

  @Binds
  @Singleton
  abstract fun bindMusicFlowFeedSource(
    impl: MusicFlowRepository
  ): MusicFlowFeedSource
}
