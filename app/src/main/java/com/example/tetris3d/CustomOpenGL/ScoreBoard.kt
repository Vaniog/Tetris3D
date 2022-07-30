package com.example.tetris3d.CustomOpenGL

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.customopengl.Space
import com.example.tetris3d.GameActivity
import kotlin.math.PI
import kotlin.math.sin

class ScoreBoard(var need : Int = -1, val activity : GameActivity) : Space(){
    var speed = 1.0
    var score = 0

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
        var str = "Score: $score"
        var str2 = ""
        if (need != -1) {
            str += " (You need $need)\n"
            activity.updateCampaignTime()
            val timePassed = activity.currentTime / 1000
            str2 += "Time: $timePassed"
        }
        val paint = Paint()
        paint.color = Color.WHITE
        paint.strokeWidth = 1f
        paint.textSize = 35f * textAnim.getScale(deltaTime).toFloat()
        canvas.drawText(str, 0, str.length, 50f, 50f, paint)
        if (str2 != "")
         canvas.drawText(str2, 0, str2.length, 50f, 90f, paint)
    }

    fun clear(){
        score = 0
        speed = 1.0
    }
    fun scored(){
        score++
        textAnim.launch();
        speed *= 1.1
        if (score == need){
            activity.finish();
            activity.levelPassed();
        }
    }
}