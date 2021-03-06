package com.example.githubuserapp.database

import android.database.Cursor
import androidx.room.*

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourite")
    fun getAll(): List<Favourite>

    @Query("SELECT * FROM  favourite")
    fun queryAll(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg favourite: Favourite)

    @Query("SELECT EXISTS (SELECT 1 FROM favourite WHERE id = :id)")
    fun exists(id: Long): Boolean

    @Query("Delete FROM favourite where id = :id")
    fun delete(id: Long)

}