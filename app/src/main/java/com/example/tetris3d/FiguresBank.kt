package com.example.tetris3d

import android.graphics.Color

//хранит различные фигуры
class FiguresBank {
    private var list = mutableListOf<Figure>()
    private var colorList = mutableListOf<Int>(Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN, Color.MAGENTA)
    init {
        fillBank()
    }

    fun getFigure() : Figure {
        val x = list[ (0 until list.size).random()]
        x.changeColor(colorList[(0 until colorList.size).random()])
        return x
    }

    private fun fillBank(){
        list.clear()
        //очень тупое добавление фигур)
        val figure1 = Figure()
        var figure2d  = arrayOf(arrayOf(0, 1, 1), arrayOf(1, 1, 0)) // зигзаг
        figure1.convertFrom2d(figure2d, 1, 3)
        list.add(figure1)

        val figure2 = Figure()
        figure2d = arrayOf(arrayOf(1, 1), arrayOf(1, 1)) // квадрат
        figure2.convertFrom2d(figure2d, 2, 2)
        list.add(figure2)

        val figure3 = Figure()
        figure2d = arrayOf(arrayOf(1, 0, 0), arrayOf(1, 1, 1)) // г
        figure3.convertFrom2d(figure2d, 3, 3)
        list.add(figure3)

        val figure4 = Figure()
        figure2d = arrayOf(arrayOf(1, 1, 1), arrayOf(0, 1, 0)) // т
        figure4.convertFrom2d(figure2d, 1, 3)
        list.add(figure4)

        val figure5 = Figure()
        figure2d = arrayOf(arrayOf(1, 1, 1, 1)) // палка
        figure5.convertFrom2d(figure2d, 0, 4)
        list.add(figure5)

    }
}

