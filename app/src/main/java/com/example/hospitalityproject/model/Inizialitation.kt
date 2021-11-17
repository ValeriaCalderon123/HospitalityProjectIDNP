package com.example.hospitalityproject.model

import android.app.Application

class Initialization: Application() {

    companion object{
        lateinit var pref:PreferenceModel
    }

    override fun onCreate() {
        super.onCreate()
        pref= PreferenceModel(applicationContext)
    }
}