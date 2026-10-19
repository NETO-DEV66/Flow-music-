package com.flowtune.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.flowtune.core.repository.PlaylistRepositoryImpl
import com.flowtune.core.repository.SongRepositoryImpl
import com.flowtune.domain.repositories.PlaylistRepository
import com.flowtune.domain.repositories.SongRepository
import com.flowtune.app.musicflow.MusicFlowFeedSource
import com.flowtune.app.musicflow.MusicFlowRepository
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
