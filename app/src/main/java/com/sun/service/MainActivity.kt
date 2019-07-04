package com.sun.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var checkBound = false
    private lateinit var conn:ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerListener()
    }

    private fun registerListener() {
        btnStart.setOnClickListener(this)
        btnStop.setOnClickListener(this)
        btnStartBound.setOnClickListener(this)
        btnSong.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        val intent = Intent(this, BoundService::class.java)
        //val intent = Intent(this, MyService::class.java)
        intent.putExtra("DEMO", "HOI")
        when (p0.id) {
            R.id.btnStart -> {
                startService(intent)
            }
            R.id.btnStop -> {
                //stopService(intent)
                if (checkBound) {
                    unbindService(conn)
                    checkBound = false
                }


            }
            R.id.btnStartBound -> {
                 conn = object : ServiceConnection {
                    override fun onServiceDisconnected(p0: ComponentName?) {
                        checkBound = false
                        Log.d("MainActivity", "onServiceDisconnected")

                    }

                    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                        checkBound = true
                        Log.d("MainActivity", "onServiceConnected")
                        val boundExample = p1 as BoundService.BoundExample
                        val boundService = boundExample.getService()
                        boundService.show()

                    }

                }
                bindService(intent, conn, Context.BIND_AUTO_CREATE)

            }
            R.id.btnSong->{
                startActivity(Intent(this,SongActivity::class.java))
            }
        }

    }
}
