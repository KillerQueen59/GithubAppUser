package com.example.githubuserapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.adapter.PagerAdapter
import com.example.githubuserapp.connection.ApiService
import com.example.githubuserapp.database.Favourite
import com.example.githubuserapp.database.FavouriteDatabase
import com.example.githubuserapp.databinding.ActivityUserDetailBinding
import com.example.githubuserapp.model.User
import com.example.githubuserapp.viewmodel.UserViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.jetbrains.anko.toast


class UserDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private val model: UserViewModel by viewModels()
    private lateinit var data: User
    lateinit var session: SessionManagement
    var  fromfav: Boolean = false
    var exists: Boolean = false
    private var favouriteDatabase: FavouriteDatabase? = null
    val compositeDisposable = CompositeDisposable()

    private val apiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        session = SessionManagement(applicationContext)
        favouriteDatabase = FavouriteDatabase.getInstance(this)

        val user: String? = intent.getStringExtra("username")
        fromfav = intent.getBooleanExtra("fromfav",false)
        binding.username.text = user
        getDetailUser(user.toString())
        binding.pager.adapter = PagerAdapter(supportFragmentManager)
        binding.tablayout.setupWithViewPager(binding.pager)
        binding.btnFavourite.setOnClickListener {
            if (!exists) {
                insertToDb(Favourite(data.name,data.login,data.public_repos,data.avatar_url,data.company,data.location,data.followers,data.following,data.id))
                binding.btnFavourite.background = ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite_24)
                toast("Add to Favourite")
            }else {
                deleteDb(data.id)
                binding.btnFavourite.background = ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite_border_24)
                toast("Delete from Favourite")
            }
        }
    }
    fun insertToDb(favourite: Favourite){
        compositeDisposable.add(Completable.fromRunnable { favouriteDatabase?.favouriteDao()?.insertAll(favourite) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{
                Log.d("anjing","gagal")
            }))
    }

    fun deleteDb(id: Long){
        compositeDisposable.add(Completable.fromRunnable { favouriteDatabase?.favouriteDao()?.delete(id) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{
                Log.d("anjing","gagal")
            }))
    }

    fun checkExist(id:Long) : Boolean = favouriteDatabase?.favouriteDao()?.exists(id) ?: false

    fun checkButton(exist: Boolean){
        if (exist)binding.btnFavourite.background = ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite_24)
        else binding.btnFavourite.background = ContextCompat.getDrawable(this,R.drawable.ic_baseline_favorite_border_24)
    }

    private fun getDetailUser(username: String){
        showProgress(true)
        model.detailUser(apiService,username)
        model.resultDetail.observe({lifecycle},{
            if (it.result){
                data = it
                exists = checkExist(data.id)
                checkButton(exists)
                Log.d("medri",it.toString())
                setData()
                binding.tablayout.getTabAt(0)?.text = "${resources.getString(R.string.followers)} \n ${data.followers}"
                binding.tablayout.getTabAt(1)?.text = "${resources.getString(R.string.following)} \n ${data.following}"
                session.sendUser(username)
                Log.d("caps",data.toString())
                showProgress(false)
            }
        })
    }

    private fun setData(){
        binding.name.text = data.name
        binding.repo.text = data.public_repos.toString() ?: "0"
        binding.company.text = data.company ?:"-"
        binding.location.text = data.location ?: "-"
        Glide.with(binding.root).load(data.avatar_url).apply(RequestOptions().circleCrop()).into(binding.imageDetail)
    }

    private fun showProgress(show: Boolean) {
        binding.loadLayout.bringToFront()
        binding.loadLayout.visibility = if (show) View.VISIBLE else View.GONE
        binding.tablayout.visibility = if(show ) View.GONE else View.VISIBLE
        binding.pager.visibility = if(show ) View.GONE else View.VISIBLE
        binding.linearLayout.visibility = if(show ) View.GONE else View.VISIBLE
        binding.linearLayout3.visibility = if(show ) View.GONE else View.VISIBLE
        binding.linearLayout4.visibility = if(show ) View.GONE else View.VISIBLE
        binding.linearLayout5.visibility = if(show ) View.GONE else View.VISIBLE
        binding.imageDetail.visibility = if(show ) View.GONE else View.VISIBLE
    }
}