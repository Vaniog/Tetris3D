package com.example.tetris3d.CustomOpenGL

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.customopengl.Space

class ScoreBoard : Space(){
    var speed = 1.0
    var score = 0
    override fun fillCanvas(canvas: Canvas) {
        val str = "Score : $score"
        val paint = Paint()
        paint.color = Color.WHITE
        paint.strokeWidth = 1f
        paint.textSize = 35f
        canvas.drawText(str, 0, str.length, 50f, 50f, paint)
    }

    fun clear(){
        score = 0
        speed = 1.0
    }
    fun scored(){
        score++
        speed *= 1.1
    }
}