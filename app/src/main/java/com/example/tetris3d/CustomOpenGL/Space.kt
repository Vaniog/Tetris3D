package com.example.customopengl

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.MotionEvent
import kotlin.math.PI
import kotlin.math.tan
import kotlin.math.cos
import kotlin.math.sin


open class Space {
    private var matrixStack = MutableList(0) { MatrixMath(arrayOf(arrayOf())) }
    private var colorStack = MutableList(1){Color.WHITE}
    private var objects = MutableList(0) { DrawableObject() }
    private val camera = Camera()
    var color = Color.WHITE
    var stopped = false

    open fun onFrame(){

    }

    open fun onTouchEvent(event: MotionEvent?, width : Int, height : Int) : Boolean{
        return true
    }

    fun color3d(r : Int, g : Int, b : Int){
        colorStack[colorStack.size - 1] = Color.rgb(r, g, b)
        color = colorStack[colorStack.size - 1]
    }

    fun color3d(newColor : Int){
        colorStack[colorStack.size - 1] = newColor
        color = colorStack[colorStack.size - 1]
    }

    var oldTime = System.currentTimeMillis().toDouble() / 1000.0
    var time = System.currentTimeMillis().toDouble() / 1000.0
    var deltaTime = 0.0
    fun Timer(){
        val t = System.currentTimeMillis().toDouble() / 1000.0
        time = t
        deltaTime = time - oldTime
        oldTime = time
    }


    open fun fillCanvas(canvas: Canvas){
        objects.clear()
        matrixStack.clear()
        matrixStack.add(MatrixMath(arrayOf(
            arrayOf(1.0, 0.0, 0.0, 0.0),
            arrayOf(0.0, 1.0, 0.0, 0.0),
            arrayOf(0.0, 0.0, 1.0, 0.0),
            arrayOf(0.0, 0.0, 0.0, 1.0))))
        Timer()
        onFrame()

        val doComp = DOComp()
        objects.sortWith(doComp)
        for (obj in objects){
            obj.draw(canvas, canvas.width.toDouble(), canvas.width / 2f, canvas.height / 2f)
        }
    }

    fun triangle(p1x : Double, p1y : Double, p1z : Double,
                         p2x : Double, p2y : Double, p2z : Double,
                         p3x : Double, p3y : Double, p3z : Double){
        val triangle = DrawableTriangle(Point(p1x, p1y, p1z), Point(p2x, p2y, p2z), Point(p3x, p3y, p3z), color)
        triangle.transform(matrixStack[matrixStack.lastIndex])
        triangle.normalize()
        if (triangle.getZ() >= 1)
            return
        triangle.transform(camera.frustumMatrix)
        if (!triangle.check())
            return
        objects.add(triangle)
    }
    fun triangle(p1x : Int, p1y : Int, p1z : Int,
                  p2x : Int, p2y : Int, p2z : Int,
                  p3x : Int, p3y : Int, p3z : Int){
        val triangle = DrawableTriangle(Point(p1x, p1y, p1z), Point(p2x, p2y, p2z), Point(p3x, p3y, p3z), color)
        triangle.transform(matrixStack[matrixStack.lastIndex])
        triangle.normalize()
        if (triangle.getZ() >= 1)
            return
        triangle.transform(camera.frustumMatrix)
        if (!triangle.check())
            return
        objects.add(triangle)
    }

    fun square(p1x : Double, p1y : Double, p1z : Double,
                 p2x : Double, p2y : Double, p2z : Double,
                 p3x : Double, p3y : Double, p3z : Double,
                 p4x : Double, p4y : Double, p4z : Double){
        val square = DrawableSquare(Point(p1x, p1y, p1z), Point(p2x, p2y, p2z), Point(p3x, p3y, p3z), Point(p4x, p4y, p4z), color)
        square.transform(matrixStack[matrixStack.lastIndex])
        square.normalize()
        if (square.getZ() >= 1)
            return
        square.transform(camera.frustumMatrix)
        if (!square.check())
            return
        objects.add(square)
    }

    fun square(p1x : Int, p1y : Int, p1z : Int,
               p2x : Int, p2y : Int, p2z : Int,
               p3x : Int, p3y : Int, p3z : Int,
               p4x : Int, p4y : Int, p4z : Int){
        val square = DrawableSquare(Point(p1x, p1y, p1z), Point(p2x, p2y, p2z), Point(p3x, p3y, p3z), Point(p4x, p4y, p4z), color)
        square.transform(matrixStack[matrixStack.lastIndex])
        square.normalize()
        if (square.getZ() >= 1)
            return
        square.transform(camera.frustumMatrix)
        if (!square.check())
            return
        objects.add(square)
    }

    fun translate(dx : Double, dy : Double, dz : Double){
        matrixStack[matrixStack.lastIndex] = MatrixMath(arrayOf(
            arrayOf(1.0, 0.0, 0.0, 0.0),
            arrayOf(0.0, 1.0, 0.0, 0.0),
            arrayOf(0.0, 0.0, 1.0, 0.0),
            arrayOf(dx, dy, dz, 1.0))) * matrixStack[matrixStack.lastIndex]
    }

    fun rotateY(angle : Double){
        matrixStack[matrixStack.lastIndex] = MatrixMath(arrayOf(
            arrayOf(cos(angle), 0.0, sin(angle), 0.0),
            arrayOf(0.0, 1.0, 0.0, 0.0),
            arrayOf(-sin(angle), 0.0, cos(angle), 0.0),
            arrayOf(0.0, 0.0, 0.0, 1.0))) * matrixStack[matrixStack.lastIndex]
    }


    fun rotateX(angle : Double){
        matrixStack[matrixStack.lastIndex] = MatrixMath(arrayOf(
            arrayOf(1.0, 0.0, 0.0, 0.0),
            arrayOf(0.0, cos(angle), -sin(angle), 0.0),
            arrayOf(0.0, sin(angle), cos(angle), 0.0),
            arrayOf(0.0, 0.0, 0.0, 1.0))) * matrixStack[matrixStack.lastIndex]
    }

    fun rotateZ(angle : Double){
        matrixStack[matrixStack.lastIndex] = MatrixMath(arrayOf(
            arrayOf(cos(angle), -sin(angle), 0.0, 0.0),
            arrayOf(sin(angle), cos(angle), 0.0, 0.0),
            arrayOf(0.0, 0.0, 1.0, 0.0),
            arrayOf(0.0, 0.0, 0.0, 1.0))) * matrixStack[matrixStack.lastIndex]
    }
    fun zoom(sx: Double, sy : Double, sz : Double){
        matrixStack[matrixStack.lastIndex] = MatrixMath(arrayOf(
            arrayOf(sx, 0.0, 0.0, 0.0),
            arrayOf(0.0, sy, 0.0, 0.0),
            arrayOf(0.0, 0.0, sz, 0.0),
            arrayOf(0.0, 0.0, 0.0, 1.0))) * matrixStack[matrixStack.lastIndex]

    }
    fun pushMatrix(){
        matrixStack.add(matrixStack[matrixStack.lastIndex])
        colorStack.add(colorStack[colorStack.lastIndex])
    }
    fun popMatrix(){
        matrixStack.removeAt(matrixStack.lastIndex)
        colorStack.removeAt(colorStack.lastIndex)
    }
}

class Camera {


    private var near = 0.1
    private var far = 20.0
    private var fov = 3 * PI / 4.0
    private var aspect = 1.0

    var frustumMatrix :MatrixMath = MatrixMath(arrayOf(
        arrayOf(1 / (aspect * tan(fov / 2.0)), 0.0                  , 0.0                        , 0.0),
        arrayOf(0.0                             , 1 / tan(fov / 2.0), 0.0                        , 0.0),
        arrayOf(0.0                             , 0.0                  , (far + near) / (far - near), 2 * far * near / (far - near)),
        arrayOf(0.0                             , 0.0                  , -1.0                       , 0.0)))

}