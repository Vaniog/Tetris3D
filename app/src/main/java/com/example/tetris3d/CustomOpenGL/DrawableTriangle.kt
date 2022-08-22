package com.example.customopengl

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

class DrawableTriangle(
    private var point1 : Point, private var point2: Point, private var point3: Point,
    private var color : Int) : DrawableObject(){
    var normal = Vector(0.0, 0.0, 1.0)




    override fun transform(matrix: MatrixMath) {
        var matrix1 = MatrixMath(arrayOf(arrayOf(point1.x, point1.y, point1.z, 1.0)))
        var matrix2 = MatrixMath(arrayOf(arrayOf(point2.x, point2.y, point2.z, 1.0)))
        var matrix3 = MatrixMath(arrayOf(arrayOf(point3.x, point3.y, point3.z, 1.0)))

        matrix1 *= matrix
        matrix2 *= matrix
        matrix3 *= matrix

        point1 = Point(matrix1.array[0][0] / matrix1.array[0][3],
            matrix1.array[0][1] / matrix1.array[0][3],
            matrix1.array[0][2] / matrix1.array[0][3])

        point2 = Point(matrix2.array[0][0] / matrix2.array[0][3],
            matrix2.array[0][1] / matrix2.array[0][3],
            matrix2.array[0][2] / matrix2.array[0][3])

        point3 = Point(matrix3.array[0][0] / matrix3.array[0][3],
            matrix3.array[0][1] / matrix3.array[0][3],
            matrix3.array[0][2] / matrix3.array[0][3])
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
        var k = (normal.z + 1.0) / 2.0
        if (k < 0.5)
            return
        k *= k * k
        paint.color = Color.argb(color.alpha, (color.red * k.toFloat()).toInt(), (color.green * k.toFloat()).toInt(), (color.blue * k.toFloat()).toInt())
        canvas.apply {
            val path = Path()
            //path.fillType = Path.FillType.EVEN_ODD
            path.moveTo(
                cx + (width * point1.x).toFloat(),
                cy - (width * point1.y).toFloat())
            path.lineTo(
                cx + (width * point2.x).toFloat(),
                cy - (width * point2.y).toFloat())
            path.lineTo(
                cx + (width * point3.x).toFloat(),
                cy - (width * point3.y).toFloat())
            drawPath(path, paint)
        }
    }

    override fun getZ() : Double{
        return (point1.z + point2.z + point3.z) / 3.0
    }

}