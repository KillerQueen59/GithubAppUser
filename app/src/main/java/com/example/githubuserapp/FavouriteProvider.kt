package com.example.githubuserapp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuserapp.database.FavouriteDao
import com.example.githubuserapp.database.FavouriteDatabase

class FavouriteProvider: ContentProvider() {

    private lateinit var favouriteDao: FavouriteDao
    private lateinit var favouriteDatabase: FavouriteDatabase


    companion object {
        private val AUTHORITY = "com.example.githubuserapp"
        private val TABLE_NAME = "favourite"
        private val FAVOURITES = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply { addURI(AUTHORITY, TABLE_NAME, FAVOURITES) }
    }

    override fun onCreate(): Boolean {
        favouriteDatabase = FavouriteDatabase.getInstance(context as Context)!!
        favouriteDao = favouriteDatabase.favouriteDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            FAVOURITES -> favouriteDao.queryAll()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException()
    }
}