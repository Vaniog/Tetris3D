package com.example.tetris3d

//фигура - хранит трехмерный массив с цветами ее точек
//также способна проводить с собой преобразования
//координаты центра и фигуры увеличены вдвое для удобного счета
class Figure{
    private var shape = Array(1){Array(1){ Array(1){Color(-1, -1, -1)} }}
    private var center: Point = Point(0, 0, 0)
    var coordinates = Point (0, 0, 0)

    //создает пустую фигуру с заданными размерами
    fun emptyFill (width : Int, height : Int){
        shape = Array(width){Array(height){ Array(1){Color(-1, -1, -1)} }}
    }

    //создает фигуру из двумерного массива, также требуются координаты "центра"
    fun convertFrom2d (converted : Array<Array<Int>>, centerX : Int, centerY : Int){
        this.center.x = centerX
        this.center.y = centerY
        this.center.z = (centerX) % 2
        coordinates.y - centerY
        val figureArray = Array(converted.size) { Array(converted[0].size) { Array(1) { Color(-1, -1, -1) } } }
        for (x in (converted.indices))
            for (y in (converted[0].indices)) {
                if (converted[x][y] == 1)
                    figureArray[x][y][0] = Color(0, 0, 0)
            }
        shape = figureArray
    }

}