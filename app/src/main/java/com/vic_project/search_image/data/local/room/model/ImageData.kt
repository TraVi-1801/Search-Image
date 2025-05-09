package com.vic_project.search_image.data.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import com.vic_project.search_image.data.local.room.BaseDAO
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "image_db")
class ImageData (
    id: Long = 0,
    @ColumnInfo(name = "image_name") var photographer: String = "",
    @ColumnInfo(name = "image_url") var url: String = "",
    @ColumnInfo(name = "image_description") var description: String = ""
){
    @PrimaryKey
    @ColumnInfo(name= "network_id")
    var imageId: Long = id

    @Dao
    interface DAO : BaseDAO<ImageData> {
        @Query("SELECT * FROM image_db")
        fun getImages(): Flow<List<ImageData>>

        @Query("DELETE FROM image_db")
        fun deleteAll()
    }
}