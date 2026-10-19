package com.flowtune.app.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.flowtune.app.lyrics.LyricsHelper

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LyricsHelperEntryPoint {
  fun lyricsHelper(): LyricsHelper
}
