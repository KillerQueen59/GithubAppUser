package com.example.consumerapp.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.example.consumerapp"
    const val SCHEME = "content"

    class FavouriteColumns : BaseColumns {
        companion object{
            const val TABLE_NAME = "favourite"
            const val ID = "id"
            const val NAME = "name"
            const val USERNAME = "username"
            const val REPOSITORY = "repository"
            const val PICTURE = "picture"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }

    }
}