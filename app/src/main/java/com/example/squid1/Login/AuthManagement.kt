package com.example.squid1.Login

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.FragmentActivity
import com.auth0.android.jwt.JWT
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthManagement  {
    companion object {


        fun getToken(activity: FragmentActivity): String? {
            val prefs = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            return prefs.getString("token", "noToken")
        }

        fun saveToken(token: String, activity: FragmentActivity) {
            val prefs: SharedPreferences =
                activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val edit: SharedPreferences.Editor = prefs.edit()
            edit.putString("token", token)
            edit.apply()
        }

        fun disconnect(activity: FragmentActivity) {
            val prefs = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val edit: SharedPreferences.Editor = prefs.edit()
            edit.remove("token")
            edit.apply()
        }

    }
}
