package com.example.tetris3d

//фигура - хранит трехмерный массив с цветами ее точек
//также способна проводить с собой преобразования
//координаты центра и фигуры увеличены вдвое для удобного счета
class Figure{
    // трехмерный массив с цветами ее точек
    var shape = Array(1){Array(1){ Array(1){Color(-1, -1, -1)} }}

    // координаты центра вращения
    private var center: Point = Point(0, 0, 0)
    //координаты точки 0 0 0 фигуры на поле
    var coordinates = Point (0, 0, 0)
    //размеры точки по осям
    var size = Point(0, 0, 0)

    //создает пустую фигуру с заданными размерами
    fun emptyFill (width : Int, height : Int){
        shape = Array(width){Array(height){ Array(1){Color(-1, -1, -1)} }}
        size.x = width
        size.y = height
        size.z = 1
    }

    //создает фигуру из двумерного массива, также требуются координаты "центра"
    fun convertFrom2d (converted : Array<Array<Int>>, centerX : Int, centerY : Int){
        center.x = centerX
        center.y = centerY
        center.z = (centerX) % 2
        val figureArray = Array(converted.size) { Array(converted[0].size) { Array(1) { Color(-1, -1, -1) } } }
        for (x in (converted.indices))
            for (y in (converted[0].indices)) {
                if (converted[x][y] == 1)
                    figureArray[x][y][0] = Color(0, 0, 0)
            }
        size.x = converted.size
        size.y = converted[0].size
        size.z = 1
        shape = figureArray
    }

    //поворачивает фигуру на оси, направление 1 или 0,
    //1 - туда же куда линии эми направлены если ток течет по данной оси, короче вроде это правило буравчика
    fun rotate(axis : Char, direction : Int) {
        if (axis == 'x') {
            if (direction == 1) {
                val newShape = Array(size.x) { Array(size.z) { Array(size.y) { Color(-1, -1, -1) } } }

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
        }

        if (axis == 'y'){
            if (direction == 1){
                val newShape = Array(size.z) { Array(size.y) { Array(size.x) { Color(-1, -1, -1) } } }
                for (x in (0 until size.x))
                    for (y in (0 until  size.y))
                        for (z in (0 until size.z)) {
                            newShape[size.z -1 - z][y][x] = shape[x][y][z]
                        }
                val newCenter = Point(size.z * 2 - center.z, center.y, center.x)
                correctCoordinates(newCenter)
                center = newCenter
                shape = newShape
                correctSizes()
                return
            }
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
}