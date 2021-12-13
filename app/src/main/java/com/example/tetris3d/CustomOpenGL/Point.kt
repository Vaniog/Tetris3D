package com.example.customopengl

import android.graphics.Color

class Point(var x : Double, var y : Double, var z : Double) {
    
    constructor (x : Double, y : Double, z : Double, color: Int) : this(x, y, z){
        this.color = color
    }
    constructor (x : Int, y : Int, z : Int) : this(x.toDouble(), y.toDouble(), z.toDouble())
    constructor (x : Int, y : Int, z : Int, color: Int) : this(x, y, z){
        this.color = color
    }


    operator fun times(matrix : MatrixMath) : Point{
        var pointArray = arrayOf(arrayOf(x, y, z))
        pointArray = (MatrixMath(pointArray) * matrix).array
        val newX = pointArray[0][0]
        val newY = pointArray[0][1]
        val newZ = pointArray[0][2]
        return Point(newX, newY, newZ)
    }
    operator fun times(r : Double) : Point{
        return Point(x * r, y * r, z * r)
    }
    operator fun plus(vector: Vector) : Point{
        return Point(x + vector.x, y + vector.y, z + vector.z)
    }
    operator fun minus(vector: Vector) : Point{
        return Point(x - vector.x, y - vector.y, z - vector.z)
    }
    var color = Color.rgb(0, 0, 0)
}