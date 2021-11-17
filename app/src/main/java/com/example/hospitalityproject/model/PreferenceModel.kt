package com.example.hospitalityproject.model

import android.content.Context

class PreferenceModel(val context: Context) {
        val SHARED_NAME = "Data"
        val SHARED_EMAIL = "email"

        val storage = context.getSharedPreferences(SHARED_NAME,0)
        fun saveEmail(first_name:String){
            storage.edit().putString(SHARED_EMAIL,first_name).apply()
        }

        fun getEmail():String{
            return storage.getString(SHARED_EMAIL,"")!!
        }

        fun wipe(){
            storage.edit().clear().apply()
        }
    }