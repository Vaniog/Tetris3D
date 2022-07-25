package com.example.tetris3d

import android.content.Context
import android.graphics.*
import com.example.customopengl.Space
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class ProgressSpace(val context: Context) : Space(){
    val startLength = 5.0;
    var length = startLength
    var pass = 0.0
    var score = 0
    val width = 0.7f;
    val height = 0.03f;
    val startX = 0.01f;
    val startY = 0.02f;
    class TextAnim(){
        val length = 0.2;
        var pass = 0.0;
        fun getScale(deltaTime : Double): Double {
            if (pass < length)
                pass += deltaTime;
                return 1.0 + sin(pass / length * PI) * 0.13;
            }
        fun launch(){
            pass = 0.0;
        }
    }
    val textAnim = TextAnim();
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
        var highestScore = context.getSharedPreferences("data", Context.MODE_PRIVATE).getInt("HIGH_SCORE_CASUAL", 0);
        if (score > highestScore) {
            context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                .putInt("HIGH_SCORE_CASUAL", score).apply()
            highestScore = score
        }
        val str1 = "Combo : $score"
        val str2 = "Best: $highestScore"
        paint.textSize = 35f * textAnim.getScale(deltaTime).toFloat()
        canvas.drawText(str1, 0, str1.length, 20f, 120f, paint)
        if (score != highestScore)
            paint.textSize = 35f
        canvas.drawText(str2, 0, str2.length, 20f, 160f, paint)
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
        length *= 0.97;
        textAnim.launch();
    }
}