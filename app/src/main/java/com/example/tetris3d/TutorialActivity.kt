package com.example.tetris3d

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity


class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val menuButton = findViewById<Button>(R.id.menuButton3)

        menuButton.setOnClickListener{
            finish()
        }
        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = "android.resource://$packageName" + "/" + R.raw.tutorial
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.requestFocus()

        videoView.setOnCompletionListener {
            videoView.start()
        }
        videoView.setOnPreparedListener{
            videoView.start()
        }
    }
}