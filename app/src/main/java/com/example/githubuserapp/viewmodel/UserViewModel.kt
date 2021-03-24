package com.example.githubuserapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.connection.ApiService
import com.example.githubuserapp.model.User
import com.example.githubuserapp.model.UserList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserViewModel : ViewModel(){
    private var disposable: Disposable? = null
    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    private val _resultSearch = MutableLiveData<UserList>()
    val resultSearch: LiveData<UserList>
        get() = _resultSearch
    private val _resultDetail = MutableLiveData<User>()
    val resultDetail: LiveData<User>
        get() = _resultDetail
    private val _resultFollow = MutableLiveData<ArrayList<User>>()
    val resultFollow: LiveData<ArrayList<User>>
        get() = _resultFollow

    fun searchUser(apiService: ApiService,query: String){
        uiScope.launch {
            disposable = apiService.searchUser(BuildConfig.BASE_URL + "search/users?q=" + query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.result = true
                    _resultSearch.value = it
                },{
                    _resultSearch.value?.message = it.message.toString() ?: " "
                })
        }
    }

    fun detailUser(apiService: ApiService, username: String){
        uiScope.launch {
            disposable = apiService.detailUser(BuildConfig.BASE_URL + "users/" + username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.result = true
                    _resultDetail.value = it
                },{
                    _resultDetail.value?.message = it.message.toString() ?: " "
                })
        }
    }

    fun followingUser(apiService: ApiService,username: String){
        uiScope.launch {
            disposable = apiService.followingUser(BuildConfig.BASE_URL + "users/" + username + "/following")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _resultFollow.value = it
                    },{

                    })
        }
    }
    fun followersUser(apiService: ApiService,username: String){
        uiScope.launch {
            disposable = apiService.followingUser(BuildConfig.BASE_URL + "users/" + username + "/followers")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        _resultFollow.value = it
                    },{

                    })
        }
    }

    fun onPaused(){
        disposable?.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}