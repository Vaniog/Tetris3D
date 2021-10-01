package com.example.tetris3d

//хранит различные фигуры
class FiguresBank() {
    private var list = mutableListOf<Figure>()
    init {
        fillBank()
    }

    fun getFigure() : Figure {
        return list[(0 until list.size).random()]
    }

    fun fillBank(){
        list.clear()
        //очень тупое добавление фигур)
        val figure = Figure()
        var figure2d  = arrayOf(arrayOf(0, 1, 1), arrayOf(1, 1, 0)) // зигзаг
        figure.convertFrom2d(figure2d, 3, 1)
        list.add(figure)

        figure2d = arrayOf(arrayOf(1, 1), arrayOf(1, 1)) // квадрат
        figure.convertFrom2d(figure2d, 2, 2)
        list.add(figure)

        figure2d = arrayOf(arrayOf(1, 0, 0), arrayOf(1, 1, 1)) // г
        figure.convertFrom2d(figure2d, 3, 3)
        list.add(figure)

        figure2d = arrayOf(arrayOf(1, 1, 1), arrayOf(0, 1, 0)) // т
        figure.convertFrom2d(figure2d, 3, 1)
        list.add(figure)

        figure2d = arrayOf(arrayOf(1, 1, 1, 1)) // палка
        figure.convertFrom2d(figure2d, 4, 0)
        list.add(figure)

    }
}

