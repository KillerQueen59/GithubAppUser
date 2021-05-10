package com.example.consumerapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.UserDetailActivity
import com.example.consumerapp.database.Favourite
import com.example.consumerapp.database.FavouriteDatabase
import com.example.consumerapp.databinding.ItemUserBinding
import com.example.consumerapp.model.User
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity

import org.jetbrains.anko.toast

class UserAdapter(private val users: List<User>,private val context: Context)
    :RecyclerView.Adapter<UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder = UserViewHolder(
        ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {
            it.context.startActivity<UserDetailActivity>("username" to users[position].login)
        }
    }

    override fun getItemCount(): Int = users.size
}

class UserViewHolder(private val itemBinding: ItemUserBinding): RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var user: User

    fun bind(user: User){
        this.user = user
        itemBinding.name.text = user.login
        Glide.with(itemBinding.root).load(user.avatar_url).apply(RequestOptions().circleCrop()).into(itemBinding.image)
    }

}
