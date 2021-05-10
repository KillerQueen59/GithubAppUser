package com.example.githubuserapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.FavouriteAdapter
import com.example.githubuserapp.database.Favourite
import com.example.githubuserapp.database.FavouriteDatabase
import com.example.githubuserapp.databinding.ActivityFavouriteBinding
import java.util.ArrayList

class FavouriteActivity: AppCompatActivity() {

    private lateinit var adapter: FavouriteAdapter
    private val favourite : ArrayList<Favourite> = ArrayList()
    private var favouriteDatabase: FavouriteDatabase? = null
    private lateinit var binding : ActivityFavouriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        favouriteDatabase = FavouriteDatabase.getInstance(this)
        favourite.clear()
        favouriteDatabase?.favouriteDao()?.getAll()?.let { favourite.addAll(it) }
        showData()
        if (favourite.size == 0){
        binding.emptyFavourite.visibility = View.VISIBLE
        } else binding.emptyFavourite.visibility = View.GONE
    }

    private fun showData(){
        binding.rvFavourite.setHasFixedSize(true)
        binding.rvFavourite.layoutManager = LinearLayoutManager(this)
        adapter = FavouriteAdapter(favourite)
        binding.rvFavourite.adapter = adapter
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }

}