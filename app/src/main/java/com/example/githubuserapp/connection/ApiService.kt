package com.example.githubuserapp.connection


import com.example.githubuserapp.model.User
import com.example.githubuserapp.model.UserList
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET
    fun searchUser(@Url url: String): Observable<UserList>

    @GET
    fun detailUser(@Url url: String): Observable<User>
    
    @GET
    fun followingUser(@Url url: String): Observable<ArrayList<User>>

    companion object{
        fun create(): ApiService{
            val interceptor =  HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY


            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(headersInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com")
                .client(client)
                .build()

            return  retrofit.create(ApiService::class.java)
        }

        private fun headersInterceptor(): Interceptor = Interceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", "token b3ef1959fc84420e964956340ba9a6754ee79efa")
                .build())
        }
    }
}