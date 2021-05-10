package com.example.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.UserDetailActivity
import com.example.consumerapp.databinding.ItemUserBinding
import com.example.consumerapp.model.User
import org.jetbrains.anko.startActivity

class FollowAdapter(private val users: List<User>)
    : RecyclerView.Adapter<FollowViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder = FollowViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(users[position])

    }

    override fun getItemCount(): Int = users.size
}

class FollowViewHolder(private val itemBinding: ItemUserBinding): RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var user: User

    fun bind(user: User){
        this.user = user
        itemBinding.name.text = user.login
        Glide.with(itemBinding.root).load(user.avatar_url).apply(RequestOptions().circleCrop()).into(itemBinding.image)
    }


}
