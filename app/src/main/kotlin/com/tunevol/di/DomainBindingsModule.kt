package com.tunevol.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.tunevol.core.repository.PlaylistRepositoryImpl
import com.tunevol.core.repository.SongRepositoryImpl
import com.tunevol.domain.repositories.PlaylistRepository
import com.tunevol.domain.repositories.SongRepository
import com.tunevol.app.musicflow.MusicFlowFeedSource
import com.tunevol.app.musicflow.MusicFlowRepository
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
