package com.example.consumerapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.adapter.FavouriteAdapter
import com.example.consumerapp.database.Favourite
import com.example.consumerapp.database.FavouriteDatabase
import com.example.consumerapp.databinding.ActivityFavouriteBinding
import com.example.consumerapp.model.User
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
        adapter = FavouriteAdapter(favourite,this)
        binding.rvFavourite.adapter = adapter
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }

}