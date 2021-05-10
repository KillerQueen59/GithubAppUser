package com.example.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserList(
    val total_count: Int,
    val incomplete_results: Boolean,
    var message: String,
    var result: Boolean,
    val items: ArrayList<User>
):Parcelable