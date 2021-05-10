package com.example.consumerapp.database

import android.content.ContentValues
import android.database.Cursor
import androidx.room.*

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite")
    fun getAll(): List<Favourite>

    @Query("SELECT * FROM favourite")
    fun queryAll(): Cursor

    @Query("SELECT * FROM favourite where id =:id")
    fun queryById(id: Long): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg favourite: Favourite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(values: Favourite?): Long

    @Update
    fun update(vararg favourite: Favourite?) : Int

    @Update
    fun updateById(id:Long,values: ContentValues?) : Int

    @Query("SELECT EXISTS (SELECT 1 FROM favourite WHERE id = :id)")
    fun exists(id: Long): Boolean

    @Query("Delete FROM favourite where id = :id")
    fun delete(id: Long):Int



}