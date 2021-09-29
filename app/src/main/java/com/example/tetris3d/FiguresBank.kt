package com.example.tetris3d

//хранит различные фигуры
class FiguresBank {

    private var list = mutableListOf<Figure>()

    fun getFigure() : Figure {
        return list[(0 until list.size).random()]
    }

    fun fillBank(){
        list.clear()
        //очень тупое добавление фигур)
        val figure = Figure()
        var figure2d  = arrayOf(arrayOf(0, 1, 1), arrayOf(1, 1, 0)) // зигзаг
        figure.convertFrom2d(figure2d)
        list.add(figure)

        figure2d = arrayOf(arrayOf(1, 1), arrayOf(1, 1)) // квадрат
        figure.convertFrom2d(figure2d)
        list.add(figure)

        figure2d = arrayOf(arrayOf(1, 0, 0), arrayOf(1, 1, 1)) // г
        figure.convertFrom2d(figure2d)
        list.add(figure)

        figure2d = arrayOf(arrayOf(1, 1, 1), arrayOf(0, 1, 0)) // т
        figure.convertFrom2d(figure2d)
        list.add(figure)

        figure2d = arrayOf(arrayOf(1, 1, 1, 1)) // палка
        figure.convertFrom2d(figure2d)
        list.add(figure)

    }
}

//фигура - хранит трехмерный массив с цветами ее точек
//также способна проводить с собой преобразования
class Figure{
    private var shape = Array(1){Array(1){ Array(1){Color(-1, -1, -1)} }}

    //создает пустую фигуру с заданными размерами
    fun emptyFill (width : Int, height : Int){
        shape = Array(width){Array(height){ Array(1){Color(-1, -1, -1)} }}
    }

    //создает фигуру из двумерного массива
    fun convertFrom2d (converted : Array<Array<Int>>){
        val figureArray = Array(converted.size) { Array(converted[0].size) { Array(1) { Color(-1, -1, -1) } } }
        for (x in (converted.indices))
            for (y in (converted[0].indices)) {
                if (converted[x][y] == 1)
                    figureArray[x][y][0] = Color(0, 0, 0)
            }
        shape = figureArray
    }
}