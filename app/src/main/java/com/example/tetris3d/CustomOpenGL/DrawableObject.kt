package com.example.customopengl

import android.graphics.Canvas

open class DrawableObject {
    open fun draw(canvas : Canvas, width : Double, cx : Float, cy: Float){
    }

    open fun getZ() : Double{
        return 0.0
    }

    open fun transform(matrix: MatrixMath) {
    }

}

class DOComp : Comparator <DrawableObject>{
    override fun compare(o1: DrawableObject, o2: DrawableObject): Int {
        val z1 = o1.getZ()
        val z2 = o2.getZ()
        if (z1 > z2)
            return 1
        if (z1 == z2)
            return 0
        return -1

    }
}
