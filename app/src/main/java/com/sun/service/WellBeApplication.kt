package com.sun.service

import android.app.Application
import android.content.Context

class WellBeApplication : Application() {

    companion object {
        private lateinit var instance: WellBeApplication
        var isPlayingSong = false
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}
