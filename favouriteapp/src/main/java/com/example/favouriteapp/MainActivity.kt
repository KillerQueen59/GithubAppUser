package com.example.favouriteapp

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favouriteapp.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        private const val AUTHORITY = "com.example.githubuserapp"
        private const val TABLE_NAME = "favourite"

        private const val NAME = "name"
        private const val USERNAME = "username"
        private const val COMPANY = "company"
        private const val LOCATION = "location"
        private const val PICTURE = "picture"
        private const val REPOSITORY = "repository"
        private const val FOLLOWERS = "followers"
        private const val FOLLOWING = "following"
        private const val ID = "id"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FavouriteAdapter
    private val favourite : ArrayList<Favourite> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showData()
        getDataFavourite()
    }

    private fun getDataFavourite(){
        val uriBuilder = Uri.Builder().apply {
            scheme("content")
            authority(AUTHORITY)
            appendPath(TABLE_NAME)
        }
        val uri = uriBuilder.build()

        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(uri,null,null,null,null)
        Log.d("sofia",cursor.toString())
        Log.d("sofia",cursor?.count.toString())

        if (cursor != null){
            if (cursor.count <= 0)  binding.emptyFavourite.visibility = View.VISIBLE
            else {
                binding.emptyFavourite.visibility = View.GONE
                favourite.clear()
                favourite.addAll(getFavourite(cursor))
                adapter.notifyDataSetChanged()
            }
        }

    }

    private fun getFavourite(cursor: Cursor): List<Favourite>{
        val list: MutableList<Favourite> = mutableListOf()
        cursor.apply {
            while (moveToNext()){
                list.add(
                    Favourite(
                        getString(getColumnIndexOrThrow(NAME)),
                        getString(getColumnIndexOrThrow(USERNAME)),
                        getInt(getColumnIndexOrThrow(REPOSITORY)),
                        getString(getColumnIndexOrThrow(PICTURE)),
                        getString(getColumnIndexOrThrow(COMPANY)),
                        getString(getColumnIndexOrThrow(LOCATION)),
                        getInt(getColumnIndexOrThrow(FOLLOWERS)),
                        getInt(getColumnIndexOrThrow(FOLLOWING)),
                        getLong(getColumnIndexOrThrow(ID)),
                    )
                )
            }
            close()
        }
        Log.d("sofia",list.toString())

        return list.toList()
    }

    private fun showData(){
        binding.rvFavourite.setHasFixedSize(true)
        binding.rvFavourite.layoutManager = LinearLayoutManager(this)
        adapter = FavouriteAdapter(favourite,this)
        binding.rvFavourite.adapter = adapter
    }
}