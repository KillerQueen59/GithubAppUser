package com.example.githubuserapp.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.githubuserapp.R
import com.example.githubuserapp.database.Favourite
import com.example.githubuserapp.database.FavouriteDatabase

internal class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory  {
    private var list: ArrayList<Favourite> = arrayListOf()
    private lateinit var favouriteDatabase: FavouriteDatabase
    private var cursor: Cursor? = null


    override fun onCreate() {
        favouriteDatabase = FavouriteDatabase.getInstance(context)!!
        cursor = favouriteDatabase.favouriteDao().queryAll()
        list = mapCursorToArrayList(cursor)
        Log.d("eyyore", list.toString())

    }

    override fun onDataSetChanged() {
        cursor = favouriteDatabase.favouriteDao().queryAll()

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = cursor!!.count

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        val bmp: Bitmap?
        try {
            bmp = Glide.with(context)
                    .asBitmap()
                    .load(list[position].picture)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get()
            rv.setImageViewBitmap(R.id.imageView, bmp)
        } catch (e: InterruptedException) {
            Log.d("Widget Load Error", "error")
        }
        val extras = Bundle()
        extras.putInt(UserFavouriteWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = if (cursor!!.moveToPosition(i)) cursor!!.getLong(0) else i.toLong()

    override fun hasStableIds(): Boolean = false

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favourite> {
        val notesList = ArrayList<Favourite>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow("name"))
                val username = getString(getColumnIndexOrThrow("username"))
                val repository = getInt(getColumnIndexOrThrow("repository"))
                val picture = getString(getColumnIndexOrThrow("picture"))
                val company = getString(getColumnIndexOrThrow("company"))
                val location = getString(getColumnIndexOrThrow("location"))
                val followers = getInt(getColumnIndexOrThrow("followers"))
                val following = getInt(getColumnIndexOrThrow("following"))
                notesList.add(Favourite(name, username, repository, picture, company, location, followers, following, id))
            }
        }
        return notesList
    }
}