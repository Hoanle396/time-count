package com.example.ktgk

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var btnStop:Button
    lateinit var btnStart:Button
    lateinit var btnPause:Button
    lateinit var txtTimer: TextView
    var lStartTime: Long = 0
    var lPauseTime:kotlin.Long = 0
    var lSystemTime:kotlin.Long = 0L
    var handler: Handler = Handler()
    var isRun = false
    val runnable: Runnable = object : Runnable {
        override fun run() {
            lSystemTime = SystemClock.uptimeMillis() - lStartTime
            val lUpdateTime: Long = lPauseTime + lSystemTime
            var secs = (lUpdateTime / 1000)
            val mins = secs / 60
            secs = secs % 60
            txtTimer.text = "" + mins + ":" + String.format("%02d", secs) + ":" + String.format(
                "%03d",
                (lUpdateTime % 1000)
            )
            handler.postDelayed(this, 0)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.pause);
        btnPause = findViewById(R.id.reset);
        txtTimer = findViewById(R.id.text);
    }

    fun start(v: View?) {
        if (isRun) return
        isRun = true
        lStartTime = SystemClock.uptimeMillis()
        handler.postDelayed(runnable, 0)
     }

    fun pause(v: View){
        if (!isRun) return
        isRun = false
        lPauseTime += lSystemTime
        handler.removeCallbacks(runnable)
    }

    fun reset(v:View){
        isRun = false
        lPauseTime = 0
        txtTimer.text="00:00:00"
        handler.removeCallbacks(runnable)
    }

}