package com.example.githubuserapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favourite::class],version = 2,exportSchema = false)
abstract class FavouriteDatabase: RoomDatabase() {
    abstract fun favouriteDao (): FavouriteDao
    companion object{
        private var INSTANCE: FavouriteDatabase? = null
        fun getInstance(context: Context): FavouriteDatabase? {
            if(INSTANCE == null){
                synchronized(FavouriteDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            FavouriteDatabase::class.java,"favouriteclass.db").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}