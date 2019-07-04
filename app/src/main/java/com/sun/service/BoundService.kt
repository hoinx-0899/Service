package com.sun.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BoundService : Service() {
    private val TAG = BoundService::class.java.simpleName
    override fun onCreate() {
        Log.d(TAG, "onCreate==========================")
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "onBind==========================")
        return BoundExample()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind==========================")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "onRebind==========================")
        super.onRebind(intent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy==========================")
        super.onDestroy()
    }

    class BoundExample : Binder() {
        fun getService(): BoundService = BoundService()
    }

    fun show() {
        Log.d(TAG, "Bound==========================")

    }

}