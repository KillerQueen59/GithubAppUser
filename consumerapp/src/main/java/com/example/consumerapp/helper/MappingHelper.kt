package com.example.consumerapp.helper

import android.database.Cursor
import androidx.room.util.CursorUtil.getColumnIndexOrThrow
import com.example.consumerapp.database.DatabaseContract
import com.example.consumerapp.database.Favourite

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favourite> {
        val list = ArrayList<Favourite>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.NAME))
                val repository = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.REPOSITORY))
                val picture = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.PICTURE))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.LOCATION))
                val followers = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.FOLLOWERS))
                val following = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.FOLLOWING))
                list.add(Favourite(name, username,repository,picture, company, location,followers,following,id))
            }
        }
        return list
    }

    fun mapCursorToObject(notesCursor: Cursor?): Favourite {
        var favourite = Favourite()
        notesCursor?.apply {
            moveToFirst()
            val id = getLong(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.ID))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.USERNAME))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.NAME))
            val repository = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.REPOSITORY))
            val picture = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.PICTURE))
            val company = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.COMPANY))
            val location = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.LOCATION))
            val followers = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.FOLLOWERS))
            val following = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.FOLLOWING))
            favourite = Favourite(name, username,repository,picture, company, location,followers,following,id)
        }
        return favourite
    }
}