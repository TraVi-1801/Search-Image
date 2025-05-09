package com.vic_project.search_image.di.core

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.vic_project.search_image.data.local.room.SearchImageDB
import com.vic_project.search_image.data.local.room.model.ImageData
import com.vic_project.search_image.data.remote.network.ConnectivityManagerNetworkMonitor
import com.vic_project.search_image.data.remote.network.NetworkMonitor
import com.vic_project.search_image.data.repositories.SearchRepositoryImpl
import com.vic_project.search_image.domain.repositories.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun provideContext(application: Application): Context

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Singleton
    @Binds
    fun bindsUserRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository

}
