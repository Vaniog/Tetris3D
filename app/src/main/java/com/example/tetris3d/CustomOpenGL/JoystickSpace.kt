package com.example.tetris3d.CustomOpenGL

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import com.example.customopengl.Space
import com.example.tetris3d.FieldSpace
import kotlin.math.*


class JoystickSpace(private val fieldSpace: FieldSpace) : Space() {
    private var rotation = 0.0

    override fun onFrame() {
        rotation = fieldSpace.fieldRotationY
    }
    private var cx = 1f
    private var cy = 1f
    private var w = 1f
    private var h = 1f
    private var k1 = 1f
    private var x1 = 1f
    private var y1 = 1f
    private var k2 = 1f
    private var x2 = 1f
    private var y2 = 1f

    private fun countVars(canvas: Canvas){
        cx = canvas.width / 2f
        cy = canvas.height * 35f / 40f
        w = canvas.width * 4f / 5f / 2f
        h = canvas.height / 6f / 4.5f * (cos (fieldSpace.fieldRotationX + PI/4.0) + 2f).toFloat()

        k1 = tan(fieldSpace.fieldRotationY - PI /4.0).toFloat() * h / w
        x1 = sqrt(h * h / (k1 * k1 + h * h / w / w))
        y1 = sqrt(h * h - x1 * x1 * h * h / w / w) * sign(k1)

        k2 = tan(fieldSpace.fieldRotationY + PI / 4.0).toFloat() * h / w
        x2 = sqrt(h * h / (k2 * k2 + h * h / w / w))
        y2 = sqrt(h * h - x2 * x2 * h * h / w / w) * sign(k2)
    }

    override fun fillCanvas(canvas: Canvas) {
        val paint = Paint()
        paint.apply {
            color = Color.rgb(132, 130, 143)
            style = Paint.Style.FILL_AND_STROKE
        }
        countVars(canvas)
        canvas.drawOval(RectF(cx - w, cy - h, cx + w, cy + h), paint)
        paint.color = Color.WHITE
        paint.strokeWidth = 3f
        canvas.drawLine(cx - x1, cy - y1, cx + x1, cy + y1, paint)
        canvas.drawLine(cx - x2, cy - y2, cx + x2, cy + y2, paint)
        paint.strokeWidth = 1f
        paint.textSize = 25f
        val str = "${"%.2f".format(fieldSpace.fieldRotationY)},\n  ${"%.2f".format(cos(fieldSpace.fieldRotationY))}, \n ${"%.2f".format(sin(fieldSpace.fieldRotationY))}"
        //canvas.drawText(str, 0, str.length - 1, 50f, 50f, paint)
    }

    var onJoystick = false
    override fun onTouchEvent(event: MotionEvent?, width: Int, height: Int): Boolean {
        if (event != null) {
            if (event.action == MotionEvent.ACTION_DOWN)
            {
                val x = event.x - cx
                val y = event.y - cy
                if (x * x / w / w + y * y / h / h > 1)
                    return true
                onJoystick = true;
            }
            if (event.action == MotionEvent.ACTION_UP && onJoystick) {
                onJoystick = false;
                val x = event.x - cx
                val y = event.y - cy
                if (x * x / w / w + y * y / h / h > 1)
                    return true
                fieldSpace.joystickTouched = true

                val up1 = y > k1 * x
                val up2 = y > k2 * x
                if (tan(fieldSpace.fieldRotationY - PI / 4.0) >= 0){
                    val s = sign(cos(fieldSpace.fieldRotationY - PI / 4.0)).toInt()
                    if (up1)
                        if (up2)
                            fieldSpace.field.moveFigure('x', 1 * s, 1, true)
                        else
                            fieldSpace.field.moveFigure('z', 1 * s, 1, true)
                    else
                        if (up2)
                            fieldSpace.field.moveFigure('z', -1 * s, 1, true)
                        else
                            fieldSpace.field.moveFigure('x', -1 * s, 1, true)
                }
                else{
                    val s = sign(cos(fieldSpace.fieldRotationY - PI / 4.0)).toInt()
                    if (up1)
                        if (up2)
                            fieldSpace.field.moveFigure('z', 1 * s, 1, true)
                        else
                            fieldSpace.field.moveFigure('x', 1 * s, 1, true)
                    else
                        if (up2)
                            fieldSpace.field.moveFigure('x', -1 * s, 1, true)
                        else
                            fieldSpace.field.moveFigure('z', -1 * s, 1, true)
                }
            }
        }
        return true
    }
}