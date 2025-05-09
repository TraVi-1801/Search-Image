package com.vic_project.search_image.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vic_project.search_image.data.local.room.model.ImageData


@Database(
    entities = [ImageData::class],
    version = 1,
)
abstract class SearchImageDB() : RoomDatabase() {
    abstract fun imageDao(): ImageData.DAO
}