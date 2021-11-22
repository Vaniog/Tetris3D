package com.example.customopengl

import kotlin.math.acos
import kotlin.math.sqrt

class Vector {
    var x = 0.0
    var y = 0.0
    var z = 0.0
    private var length = 0.0


    constructor(x : Double, y : Double, z : Double){
        this.x = x
        this.y = y
        this.z = z
        length = !this
    }

    constructor(point1: Point, point2 : Point){
        x = point2.x - point1.x
        y = point2.y - point1.y
        z = point2.z - point1.z
        length = !this
    }

    operator fun plus(vector: Vector): Vector {
        return Vector(x + vector.x, y + vector.y, z + vector.z)
    }

    operator fun minus(vector: Vector): Vector {
        return Vector(x - vector.x, y - vector.y, z - vector.z)
    }


    operator fun not() : Double{
        return sqrt(x * x + y * y + z * z)
    }

    operator fun times(vector: Vector) : Double{
        return x * vector.x + y * vector.y + z * vector.z
    }

    fun angle(vector: Vector) : Double{
        return acos(this * vector / (!this * !vector))
    }

    fun normalize(){
        x /= length
        y /= length
        z /= length
        length = 1.0
    }

    operator fun times(matrix : MatrixMath) : Vector{
        var pointArray = arrayOf(arrayOf(x, y, z))
        pointArray = (MatrixMath(pointArray) * matrix).array
        val newX = pointArray[0][0]
        val newY = pointArray[0][1]
        val newZ = pointArray[0][2]
        return Vector(newX, newY, newZ)
    }

    operator fun times(a: Double): Vector {
        return Vector(x * a, y * a, z * a)
    }


}
