package com.sun.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class DownloadIntentService : IntentService("DownloadIntentService") {

    companion object {

        private const val TAG: String = "DownloadIntentService"

        private const val ACTION_DOWNLOAD = "ACTION_DOWNLOAD"

        private const val EXTRA_URL = "EXTRA_URL"

        const val DOWNLOAD_COMPLETE = "DOWNLOAD_COMPLETE"

        const val DOWNLOAD_COMPLETE_KEY = "DOWNLOAD_COMPLETE_KEY"

        fun startActionDownload(context: Context, param: String) {
            val intent = Intent(context, DownloadIntentService::class.java).apply {
                action = ACTION_DOWNLOAD
                putExtra(EXTRA_URL, param)
            }
            context.startService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Creating Service")
    }

    override fun onDestroy() {
        Log.i(TAG, "Destroying Service")
        super.onDestroy()
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_DOWNLOAD -> {
                handleActionDownload(intent.getStringExtra(EXTRA_URL))
            }
        }
    }

    private fun handleActionDownload(param: String) {
        SongUtils.download(param)
        if(SongUtils.download(param)){
            broadcastDownloadComplete(param)
        }
    }

    private fun broadcastDownloadComplete(param: String) {
        val intent = Intent(DOWNLOAD_COMPLETE)
        intent.putExtra(DOWNLOAD_COMPLETE_KEY, param)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }
}
