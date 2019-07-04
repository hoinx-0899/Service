package com.sun.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_song.*

class SongActivity : AppCompatActivity() {
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val param = intent?.getStringExtra(DownloadIntentService.DOWNLOAD_COMPLETE_KEY)
            Log.i("SongFragment", "Received broadcast for $param")
            if (SongUtils.songFile().exists()) {
                enablePlayButton()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)
        registerListener()
    }

    private fun registerListener() {
        downloadButton.setOnClickListener {
            DownloadIntentService.startActionDownload(this, Constants.SONG_URL)
            disableMediaButtons()
            stopPlaying()
        }

        playButton.setOnClickListener {
            ContextCompat.startForegroundService(this, Intent(this, SongService::class.java))
            enableStopButton()
        }

        stopButton.setOnClickListener {
            stopPlaying()
        }
    }

    private fun stopPlaying() {
        stopService(Intent(this, SongService::class.java))
        enablePlayButton()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(WellBeApplication.getAppContext())
                .registerReceiver(receiver, IntentFilter(DownloadIntentService.DOWNLOAD_COMPLETE))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(WellBeApplication.getAppContext())
                .unregisterReceiver(receiver)
    }

    private fun enablePlayButton() {
        playButton.isEnabled = true
        stopButton.isEnabled = false
    }

    private fun enableStopButton() {
        playButton.isEnabled = false
        stopButton.isEnabled = true
    }

    private fun disableMediaButtons() {
        playButton.isEnabled = false
        stopButton.isEnabled = false
    }
}