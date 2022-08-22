package com.example.customopengl

import android.app.Activity
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.tetris3d.R
import kotlin.math.*

class DrawableSquare(
    private var point1 : Point, private var point2: Point, private var point3: Point, private var point4 : Point,
    private var color : Int, private var lineColor : Int) : DrawableObject(){
    var normal = Vector(0.0, 0.0, 1.0)


    override fun transform(matrix: MatrixMath) {
        var matrix1 = MatrixMath(arrayOf(arrayOf(point1.x, point1.y, point1.z, 1.0)))
        var matrix2 = MatrixMath(arrayOf(arrayOf(point2.x, point2.y, point2.z, 1.0)))
        var matrix3 = MatrixMath(arrayOf(arrayOf(point3.x, point3.y, point3.z, 1.0)))
        var matrix4 = MatrixMath(arrayOf(arrayOf(point4.x, point4.y, point4.z, 1.0)))

        matrix1 *= matrix
        matrix2 *= matrix
        matrix3 *= matrix
        matrix4 *= matrix

        point1 = Point(matrix1.array[0][0] / matrix1.array[0][3],
            matrix1.array[0][1] / matrix1.array[0][3],
            matrix1.array[0][2] / matrix1.array[0][3])

        point2 = Point(matrix2.array[0][0] / matrix2.array[0][3],
            matrix2.array[0][1] / matrix2.array[0][3],
            matrix2.array[0][2] / matrix2.array[0][3])

        point3 = Point(matrix3.array[0][0] / matrix3.array[0][3],
            matrix3.array[0][1] / matrix3.array[0][3],
            matrix3.array[0][2] / matrix3.array[0][3])

        point4 = Point(matrix4.array[0][0] / matrix4.array[0][3],
            matrix4.array[0][1] / matrix4.array[0][3],
            matrix4.array[0][2] / matrix4.array[0][3])
    }

    fun normalize(){
        val vector1 = Vector(point1, point2)
        val vector2 = Vector(point2, point3)
        normal = Vector(
            vector1.y * vector2.z - vector1.z * vector2.y,
            vector1.x * vector2.z - vector1.z * vector2.x,
            vector1.x * vector2.y - vector1.y * vector2.x)
        normal.normalize()
    }

    fun check() : Boolean{
        val vector1 = Vector(point1, point2)
        val vector2 = Vector(point2, point3)
        if ((vector1.x * vector2.y - vector1.y * vector2.x) < 0)
            return false
        return true
    }

    override fun draw(canvas: Canvas, width : Double, cx : Float, cy : Float) {
        val paint = Paint()
        var k = normal.z
        k = sqrt(k)
        //k = sin(k * PI / 2)

        paint.color = Color.argb(color.alpha, (color.red * k.toFloat()).toInt(), (color.green * k.toFloat()).toInt(), (color.blue * k.toFloat()).toInt())
        canvas.apply {
            val path = Path()
            //path.fillType = Path.FillType.WINDING
            path.moveTo(
                cx + (width * point1.x).toFloat(),
                cy - (width * point1.y).toFloat())
            path.lineTo(
                cx + (width * point2.x).toFloat(),
                cy - (width * point2.y).toFloat())
            path.lineTo(
                cx + (width * point3.x).toFloat(),
                cy - (width * point3.y).toFloat())
            path.lineTo(
                cx + (width * point4.x).toFloat(),
                cy - (width * point4.y).toFloat())
            path.lineTo(
                cx + (width * point1.x).toFloat(),
                cy - (width * point1.y).toFloat())
            if (color != Color.TRANSPARENT) {
                drawPath(path, paint)
            }
            paint.color = lineColor
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2f
            if (lineColor != Color.TRANSPARENT)
                drawPath(path, paint)
        }
    }

    override fun getZ() : Double{
        var ret = point1.z
        ret = min(ret, point2.z)
        ret = min(ret, point3.z)
        ret = min(ret, point4.z)
        return ret
    }

}