package com.example.tetris3d

import android.graphics.Color

//вроде как главный обрабатывающий класс
class Field(
    val width : Int,
    val height : Int
) {
    //figuresBank чтобы брать из него случайные фигуры
    private var figuresBank = FiguresBank()
    //фигура которой управляет фигура и следующая фигура
    var curFigure = figuresBank.getFigure()
    private var nextFigure = figuresBank.getFigure()

    //главное поле - хранит координаты точек и их цвета
    var field = Array(width) {Array(height) {Array(width){Color.TRANSPARENT}}}

    //обновляет состояние поля
    fun doStep() : Boolean{
        if (!moveFigure('y', 1, -1)) {
            addToField(curFigure)
            curFigure = nextFigure
            nextFigure = figuresBank.getFigure()
            nextFigure.coordinates = Point(0, 0, 0)
            if (isCollide(nextFigure))
                return false
        }
        return true
    }

    //проверяет не касается ли фигура клеток, и не вылезает ли за границы
    private fun isCollide(figure : Figure) : Boolean
    {
        for (x in (0 until figure.size.x))
            for (y in (0 until  figure.size.y))
                for (z in (0 until figure.size.z)) {
                    if (figure.shape[x][y][z] != Color.TRANSPARENT) {
                        if (!isPointOnField(Point(x + figure.coordinates.x, y + figure.coordinates.y, z + figure.coordinates.z)))
                            return true
                        if (field[x + figure.coordinates.x][y + figure.coordinates.y][z + figure.coordinates.z] != Color.TRANSPARENT)
                            return true
                    }
                }
        return false
    }

    //вставляет клетки фигуры на поле, тоесть оставляет ее на поле
    private fun addToField(figure : Figure){
        for (x in (0 until figure.size.x))
            for (y in (0 until  figure.size.y))
                for (z in (0 until figure.size.z)) {
                    if (figure.shape[x][y][z] != Color.TRANSPARENT)
                        field[x + figure.coordinates.x][y + figure.coordinates.y][z + figure.coordinates.z] = figure.shape[x][y][z]
                }
    }

    fun stupidFill() {
    }

    fun clearField() {
        field = Array(width) {Array(height) {Array(width){Color.TRANSPARENT}}}
        curFigure = figuresBank.getFigure()
        nextFigure = figuresBank.getFigure()
        nextFigure.coordinates = Point(0, 0, 0)
    }

    private fun isPointOnField(point : Point) : Boolean{
        if (point.x < 0 || point.x >= width)
            return false
        if (point.z < 0 || point.z >= width)
            return false
        if (point.y < 0 || point.y >= height)
            return false
        return true
    }

    //поворачивает главную фигуру на оси, направление 1 или -1,
    //1 - туда же куда линии эми направлены если ток течет по данной оси, короче вроде это правило буравчика
    fun rotateFigure(axis : Char, direction : Int, sign : Int) : Boolean{
        curFigure.rotate(axis, direction)
        if (isCollide(curFigure)) {
            curFigure.rotate(axis, -direction)
            return false
        }
        curFigure.launchRotation(axis, direction * sign)
        return true
    }

    //ось понятно, направление 1 - по оси -1 - против
    fun moveFigure(axis : Char, direction : Int, sign : Int) : Boolean{
        if (axis == 'y'){
            curFigure.coordinates.y += direction
            if (isCollide(curFigure)){
                curFigure.coordinates.y -= direction
                return false
            }
        }
        if (axis == 'x'){
            curFigure.coordinates.x += direction
            if (isCollide(curFigure)){
                curFigure.coordinates.x -= direction
                return false
            }
        }
        if (axis == 'z'){
            curFigure.coordinates.z += direction
            if (isCollide(curFigure)){
                curFigure.coordinates.z -= direction
                return false
            }
        }

        curFigure.launchTranslation(axis, direction * sign)
        return true
    }
}

