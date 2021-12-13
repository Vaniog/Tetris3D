package com.example.tetris3d

import android.content.Context
import android.graphics.*
import com.example.customopengl.Space

class ProgressSpace(val context: Context) : Space(){
    val startLength = 4.0;
    var length = startLength
    var pass = 0.0
    var score = 0
    val width = 0.7f;
    val height = 0.03f;
    val startX = 0.01f;
    val startY = 0.02f;

    override fun fillCanvas(canvas: Canvas) {
        Timer()
        onFrame()
        val paint = Paint();
        paint.color = Color.rgb(29, 51, 74)

        canvas.drawRoundRect(RectF(
            canvas.width * startX, canvas.height * startY,
            canvas.width * (startX + width), canvas.height * (startY + height)
        ), 20f, 20f, paint)
        paint.color = Color.rgb(58, 102, 148)
        canvas.drawRoundRect(RectF(
            canvas.width * startX, canvas.height * startY,
            canvas.width * (startX + width * (1 - pass / length).toFloat()), canvas.height * (startY + height)
        ), 20f, 20f, paint)

        paint.strokeWidth = 1f
        paint.textSize = 35f
        var highestScore = context.getSharedPreferences("data", Context.MODE_PRIVATE).getInt("HIGH_SCORE_CASUAL", 0);
        if (score > highestScore) {
            context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                .putInt("HIGH_SCORE_CASUAL", score).apply()
            highestScore = score
        }
        val str = "Combo : $score\nHighest: $highestScore"
        canvas.drawText(str, 0, str.length, 60f, 120f, paint)
    }

    override fun onFrame() {
        if (score != 0)
            pass += deltaTime;
        if (pass > length){
            pass = 0.0;
            score = 0;
            length = startLength;
        }
        super.onFrame()
    }

    fun scored(){
        score++;
        pass = 0.0;
        length *= 0.95;
    }
}