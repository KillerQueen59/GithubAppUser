package com.example.githubuserapp

import android.content.Context
import android.content.SharedPreferences

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SessionManagement( context: Context) {

    companion object{
        private val PREF_NAME = "AndroidHivePref"
        val USER_NAME = "username"
        val NOTIF_ON = "notifon"
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

    val notif: Boolean
    get() {
        return pref.getBoolean(NOTIF_ON,false)
    }

    fun setNotif(notif: Boolean){
        editor.putBoolean(NOTIF_ON,notif)
        editor.commit()
    }

    fun sendUser(username: String){
        editor.putString(USER_NAME,username)
        editor.commit()
    }

}