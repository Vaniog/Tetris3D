package com.example.tetris3d

import android.content.Context
import android.media.MediaPlayer


class Yo {
    fun Play(context: Context, Name: String = "default"){
        var mp: MediaPlayer? = null
        mp = MediaPlayer.create(context, R.raw.zombie)
        mp!!.start()
    }
}