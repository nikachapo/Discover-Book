package com.example.fincar.app

import android.app.Application

class App : Application() {

    companion object {
        lateinit var mInstance: Application
            private set

        fun getInstance(): Application {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }
}