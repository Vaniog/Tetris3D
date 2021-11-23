package com.example.tetris3d

import android.graphics.Color
import com.example.customopengl.Vector
import kotlin.math.PI
import kotlin.math.sign

//фигура - хранит трехмерный массив с цветами ее точек
//также способна проводить с собой преобразования
//координаты центра и фигуры увеличены вдвое для удобного счета
class Figure{
    // трехмерный массив с цветами ее точек
    var shape = Array(1){Array(1){ Array(1){ Color.TRANSPARENT} }}

    // координаты центра вращения
    var center: Point = Point(0, 0, 0)
    //координаты точки 0 0 0 фигуры на поле
    var coordinates = Point (0, 0, 0)
    //размеры точки по осям
    var size = Point(0, 0, 0)
    var color = Color.BLUE

    //создает пустую фигуру с заданными размерами
    fun emptyFill (width : Int, height : Int){
        shape = Array(width){Array(height){ Array(1){Color.TRANSPARENT} }}
        size.x = width
        size.y = height
        size.z = 1
    }

    //создает фигуру из двумерного массива, также требуются координаты "центра"
    fun convertFrom2d (converted : Array<Array<Int>>, centerX : Int, centerY : Int){
        center.x = centerX
        center.y = centerY
        center.z = (centerX) % 2
        val figureArray = Array(converted.size) { Array(converted[0].size) { Array(1) { Color.TRANSPARENT } } }
        for (x in (converted.indices))
            for (y in (converted[0].indices)) {
                if (converted[x][y] == 1)
                    figureArray[x][y][0] = Color.BLUE
            }
        size.x = converted.size
        size.y = converted[0].size
        size.z = 1
        shape = figureArray
    }

    //поворачивает фигуру на оси, направление 1 или -1,
    //1 - туда же куда линии эми направлены если ток течет по данной оси, короче вроде это правило буравчика
    fun rotate(axis : Char, direction : Int) {
        if (axis == 'x') {
            if (direction == 1) {
                val newShape = Array(size.x) { Array(size.z) { Array(size.y) { Color.TRANSPARENT} } }

                for (x in (0 until size.x))
                    for (y in (0 until  size.y))
                        for (z in (0 until size.z)) {
                                newShape[x][z][size.y - 1 - y] = shape[x][y][z]
                        }
                val newCenter = Point(center.x, center.z, size.y * 2 - center.y)
                correctCoordinates(newCenter)
                center = newCenter
                shape = newShape
                correctSizes()
                return
            }
            else
                for (i in (0..2))
                    rotate('x', 1)
        }

        if (axis == 'y'){
            if (direction == 1){
                val newShape = Array(size.z) { Array(size.y) { Array(size.x) { Color.TRANSPARENT } } }
                for (x in (0 until size.x))
                    for (y in (0 until  size.y))
                        for (z in (0 until size.z)) {
                            newShape[size.z - 1 - z][y][x] = shape[x][y][z]
                        }
                val newCenter = Point(size.z * 2 - center.z, center.y, center.x)
                correctCoordinates(newCenter)
                center = newCenter
                shape = newShape
                correctSizes()
                return
            }
            else
                for (i in (0..2))
                    rotate('y', 1)
        }

        if (axis == 'z'){
            if (direction == 1){
                val newShape = Array(size.y) { Array(size.x) { Array(size.z) { Color.TRANSPARENT } } }
                for (x in (0 until size.x))
                    for (y in (0 until  size.y))
                        for (z in (0 until size.z)) {
                            newShape[size.y - 1 - y][x][z] = shape[x][y][z]
                        }
                val newCenter = Point(size.y * 2 - center.y, center.x, center.z)
                correctCoordinates(newCenter)
                center = newCenter
                shape = newShape
                correctSizes()
                return
            }
            else
                for (i in (0..2))
                    rotate('z', 1)
        }
}

    fun changeColor(color: Int){
        this.color = color
        for (x in (0 until size.x))
            for (y in (0 until  size.y))
                for (z in (0 until size.z)) {
                    if (shape[x][y][z] != Color.TRANSPARENT)
                        shape[x][y][z] = color
                }
    }

    //когда фигура повернулась ее центр остался на месте, а вот координаты точки 0 0 0 изменились, надо корректировать
    private fun correctCoordinates(newCenter : Point)
    {
        coordinates.x += (center.x - newCenter.x) / 2
        coordinates.y += (center.y - newCenter.y) / 2
        coordinates.z += (center.z - newCenter.z) / 2
    }

    //когда фигура повернулась ее размеры меняются местами, эта функция по массиву точек фигуры корректирует размеры
    private fun correctSizes()
    {
        size.x = shape.size
        size.y = shape[0].size
        size.z = shape[0][0].size
    }


    val rotations = Vector(0.0, 0.0, 0.0)
    private val rotationSpeed = PI * 5
    val translations = Vector(0.0, 0.0, 0.0)
    private val translationSpeed = 10

    fun launchRotation(axis : Char, direction: Int){
        if (axis == 'x'){
            rotations.x += direction * PI / 2.0
        }
        if (axis == 'y'){
            rotations.y += direction * PI / 2.0
        }
        if (axis == 'z'){
            rotations.z += direction * PI / 2.0
        }
    }

    fun launchTranslation(axis : Char, direction: Int){
        if (axis == 'x'){
            translations.x += direction
        }
        if (axis == 'y'){
            translations.y += direction
        }
        if (axis == 'z'){
            translations.z += direction
        }
    }

    fun updateTime(deltaTime : Double){
        var lastSign : Int
        lastSign = rotations.x.sign.toInt()
        rotations.x += -lastSign * rotationSpeed * deltaTime
        if (rotations.x.sign.toInt() != lastSign)
            rotations.x = 0.0

        lastSign = rotations.y.sign.toInt()
        rotations.y += -lastSign * rotationSpeed * deltaTime
        if (rotations.y.sign.toInt() != lastSign)
            rotations.y = 0.0

        lastSign = rotations.z.sign.toInt()
        rotations.z += -lastSign * rotationSpeed * deltaTime
        if (rotations.z.sign.toInt() != lastSign)
            rotations.z = 0.0


        lastSign = translations.x.sign.toInt()
        translations.x += -lastSign * translationSpeed * deltaTime
        if (translations.x.sign.toInt() != lastSign)
            translations.x = 0.0

        lastSign = translations.y.sign.toInt()
        translations.y += -lastSign * translationSpeed * deltaTime
        if (translations.y.sign.toInt() != lastSign)
            translations.y = 0.0

        lastSign = translations.z.sign.toInt()
        translations.z += -lastSign * translationSpeed * deltaTime
        if (translations.z.sign.toInt() != lastSign)
            translations.z = 0.0
    }
}