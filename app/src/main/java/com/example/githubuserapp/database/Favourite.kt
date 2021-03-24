package com.example.githubuserapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favourite", indices = [Index(value = ["id"], unique = true)])
@Parcelize
data class Favourite(
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "username") val username: String,
        @ColumnInfo(name = "repository") val repository: Int,
        @ColumnInfo(name = "picture") val picture: String,
        @ColumnInfo(name = "company") val company: String?="",
        @ColumnInfo(name = "location") val location: String?="",
        @ColumnInfo(name = "followers") val followers: Int,
        @ColumnInfo(name = "following") val following: Int,
        @PrimaryKey @ColumnInfo(name = "id") var id: Long,
        ): Parcelable
