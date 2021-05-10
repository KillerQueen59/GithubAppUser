package com.example.consumerapp.database

import android.content.ContentValues
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
        @ColumnInfo(name = "username") var username: String = "",
        @ColumnInfo(name = "repository") var repository: Int = 0,
        @ColumnInfo(name = "picture") var picture: String = "",
        @ColumnInfo(name = "company") var company: String? = "",
        @ColumnInfo(name = "location") var location: String? = "",
        @ColumnInfo(name = "followers") var followers: Int = 0,
        @ColumnInfo(name = "following") var following: Int = 0,
        @PrimaryKey @ColumnInfo(name = "id") var id: Long = 0,
): Parcelable {
        fun fromContentValues(values: ContentValues): Favourite {
                val favourite = Favourite()
                if (values.containsKey("id")) favourite.id = values.getAsLong("id")
                if (values.containsKey("name")) favourite.name = values.getAsString("name")
                if (values.containsKey("username")) favourite.username = values.getAsString("username")
                if (values.containsKey("repository")) favourite.repository = values.getAsInteger("repository")
                if (values.containsKey("picture")) favourite.picture = values.getAsString("picture")
                if (values.containsKey("company")) favourite.company = values.getAsString("company")
                if (values.containsKey("location")) favourite.location = values.getAsString("location")
                if (values.containsKey("followers")) favourite.followers = values.getAsInteger("followers")
                if (values.containsKey("following")) favourite.following = values.getAsInteger("following")
                return favourite
        }

}

