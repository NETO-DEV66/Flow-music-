package com.flowtune.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.flowtune.app.utils.NetworkConnectivityObserver
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideNetworkConnectivityObserver(
    @ApplicationContext context: Context
  ): NetworkConnectivityObserver {
    return NetworkConnectivityObserver(context)
  }
}
