package com.example.kotlin_tmap.base

import android.app.Activity
import android.app.Application
import android.content.Context

class App :Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun context(): Context = applicationContext

    

}