package com.example.favouriteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.favouriteapp.databinding.ItemUserBinding


class FavouriteAdapter(private val users: List<Favourite>, private val context: Context)
    : RecyclerView.Adapter<FavouriteViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder = FavouriteViewHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount(): Int = users.size
}

class FavouriteViewHolder(private val itemBinding: ItemUserBinding): RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var user: Favourite

    fun bind(user: Favourite){
        this.user = user
        itemBinding.name.text = user.username
        Glide.with(itemBinding.root).load(user.picture).apply(RequestOptions().circleCrop()).into(itemBinding.image)
    }

}