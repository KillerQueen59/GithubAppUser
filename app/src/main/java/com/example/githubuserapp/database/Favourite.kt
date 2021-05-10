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
        @ColumnInfo(name = "name") var name: String = "",
        @ColumnInfo(name = "username") var username: String ="",
        @ColumnInfo(name = "repository") var repository: Int = 0,
        @ColumnInfo(name = "picture") var picture: String ="",
        @ColumnInfo(name = "company") var company: String? = "",
        @ColumnInfo(name = "location") var location: String? = "",
        @ColumnInfo(name = "followers") var followers: Int = 0,
        @ColumnInfo(name = "following") var following: Int = 0,
        @PrimaryKey @ColumnInfo(name = "id") var id: Long = 0,
): Parcelable
