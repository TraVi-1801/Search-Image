package com.vic_project.search_image.data.local.room

import androidx.room.*

@Dao
interface BaseDAO<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coll: Collection<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(array: Array<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(t: T)

    @Delete
    fun delete(t: T)

}