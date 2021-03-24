package com.example.githubuserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    var result: Boolean,
    var message: String,
    val id: Long,
    var login: String,
    var avatar_url: String,
    var name: String,
    var company: String,
    var location: String,
    var email: String,
    var followers: Int,
    var following: Int,
    var public_repos: Int,
    var click: Int
):Parcelable
