package com.example.consumerapp

import android.content.Context
import android.content.SharedPreferences

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SessionManagement( context: Context) {

    companion object{
        private val PREF_NAME = "AndroidHivePref"
        val USER_NAME = "username"
    }

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    private var PRIVATE_MODE = 0

    init {
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        editor = pref.edit()
    }

    val user: String
        get() {
            return pref.getString(USER_NAME,"").toString()
        }

    fun sendUser(username: String){
        editor.putString(USER_NAME,username)
        editor.commit()
    }

}