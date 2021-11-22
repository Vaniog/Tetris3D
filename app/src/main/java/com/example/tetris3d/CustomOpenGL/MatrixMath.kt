package com.example.customopengl

class MatrixMath(array_: Array<Array<Double>>) {
    var array = array_
    private var width = array_[0].size
    private var height = array_.size

    operator fun plus(matrix : MatrixMath) : MatrixMath {
        if (matrix.width !=  width || matrix.height != height)
            return emptyMatrix()
        val newArray = Array(height){Array(width){0.0}}
        for (x in 0 until width)
            for (y in 0 until height)
                newArray[y][x] = array[y][x] + matrix.array[y][x]
        return MatrixMath(newArray)
    }

    operator fun minus(matrix : MatrixMath) : MatrixMath {
        if (matrix.width !=  width || matrix.height != height)
            return emptyMatrix()
        val newArray = Array(height){Array(width){0.0}}
        for (x in 0 until width)
            for (y in 0 until height)
                newArray[y][x] = array[y][x] - matrix.array[y][x]
        return MatrixMath(newArray)
    }

    operator fun times(matrix : MatrixMath) : MatrixMath{
        if (width != matrix.height)
            return emptyMatrix()

        val newArray = Array(height){Array(matrix.width){0.0}}
        for (x in 0 until matrix.width)
            for (y in 0 until height) {
                for (i in 0 until width) {
                    newArray[y][x] += array[y][i] * matrix.array[i][x]
                }
            }
        return MatrixMath(newArray)
    }

    fun algComp(xd : Int, yd : Int) : MatrixMath{
        val newArray = Array(height - 1){Array(width - 1){0.0}}
        var xNew = 0
        var yNew = 0
        for (x in 0 until width) {
            if (x == xd)
                continue
            for (y in 0 until width) {
                if (y == yd)
                    continue

                newArray[yNew][xNew] = array[y][x]
                yNew++
            }
            xNew++
            yNew = 0
        }
        return MatrixMath(newArray)
    }

    private fun emptyMatrix() : MatrixMath{
        return MatrixMath(arrayOf(arrayOf()))
    }

    // только для матриц 2 x 2
    fun det2x2() : Double{
        if (width != 2 || height != 2)
            return -1.0
        return array[0][0] * array[1][1] - array[0][1] * array[1][0]
    }
}