package com.vic_project.search_image.di.core

import android.content.Context
import androidx.room.Room
import com.vic_project.search_image.data.local.room.SearchImageDB
import com.vic_project.search_image.data.local.room.model.ImageData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SearchImageDB {
        return Room.databaseBuilder(context, SearchImageDB::class.java, "search_image_database")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideNetworkDao(database: SearchImageDB): ImageData.DAO {
        return database.imageDao()
    }
}