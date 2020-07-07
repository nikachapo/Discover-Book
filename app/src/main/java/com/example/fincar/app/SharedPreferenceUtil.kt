package com.example.fincar.app

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceUtil {
    const val KEY_SUGGESTIONS = "suggestions"

    val sharedPreference: SharedPreferences by lazy {
        App.mInstance.applicationContext
            .getSharedPreferences("com.example.fincar", Context.MODE_PRIVATE)
    }

    val editor: SharedPreferences.Editor by lazy {
        sharedPreference.edit()
    }

    fun putString(key: String, value: String){
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String): String? {
        return sharedPreference.getString(key, "")
    }

    fun putStringSet(key: String, values: MutableSet<String>){
        editor.putStringSet(key, values)
        editor.commit()
    }

    fun getStringSet(key: String): MutableSet<String>? {
        return sharedPreference.getStringSet(key, mutableSetOf("Romeo", "Harry Potter"))
    }

    fun removeString(key: String){
        if(keyExists(key)) {
            editor.remove(key)
            editor.commit()
        }
    }

    fun keyExists(key: String) = sharedPreference.contains(key)
}