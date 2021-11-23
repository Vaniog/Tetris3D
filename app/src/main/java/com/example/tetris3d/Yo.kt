package com.example.tetris3d

import android.content.Context
import android.media.MediaPlayer

import com.example.tetris3d.R.raw.zombie
import com.example.tetris3d.R.raw.la


class Yo {
    fun Play(context: Context, Name: Int = zombie){
        var mp: MediaPlayer? = null
        mp = MediaPlayer.create(context, la)
        mp!!.start()
    }
}