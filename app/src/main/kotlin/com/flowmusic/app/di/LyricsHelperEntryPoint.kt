package com.flowmusic.app.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.flowmusic.app.lyrics.LyricsHelper

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LyricsHelperEntryPoint {
  fun lyricsHelper(): LyricsHelper
}
