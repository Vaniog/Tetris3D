package com.example.tetris3d

import android.graphics.Color

//вроде как главный обрабатывающий класс
class Field(
        val width : Int,
    val height : Int,
    val playHeight : Int,
) {
    //figuresBank чтобы брать из него случайные фигуры
    private var figuresBank = FiguresBank()
    //фигура которой управляет фигура и следующая фигура
    var curFigure = figuresBank.getFigure()
    private var nextFigure = figuresBank.getFigure()

    //главное поле - хранит координаты точек и их цвета
    var field = Array(width) {Array(height) {Array(width){Color.TRANSPARENT}}}
    var gameEnded = false
    private val endTickTime = 0.2
    private var gameEndTimePassed = 0.0
    private var ticks = 0
    private val maxTick = 30
    private var tickColor = Color.BLACK
    //обновляет состояние поля
    fun doStep(fast : Boolean) : Int{
        if (!gameEnded){
            if (!moveFigure('y', 1, -1, !fast)) {
                addToField(curFigure)
                if (curFigure.coordinates.y < height - playHeight - 1) {
                    gameEnded = true;
                    return 1;
                }
                curFigure = nextFigure
                nextFigure = figuresBank.getFigure()
                nextFigure.coordinates = Point(0, 0, 0)
                if (isCollide(nextFigure))
                    return 0
                return 1
            }
        }
        else{
            ticks++
            var buf = curFigure.color
            curFigure.color = tickColor
            tickColor = buf
            gameEndTimePassed = 0.0;
            if (ticks > maxTick)
                return 0
        }
        if (checkField())
            return 2
        return 3
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

    fun checkField() : Boolean{
        for (y in (0 until height)){
            if (field[0][y][0] == Color.WHITE)
                for(i in y downTo 1)
                    for (x in 0 until width)
                        for (z in 0 until width) {
                            field[x][i][z] = field[x][i - 1][z]
                        }
            else {
                var checker = true
                for (x in 0 until width)
                    for (z in 0 until width)
                        if (field[x][y][z] == Color.TRANSPARENT)
                            checker = false
                if (checker) {
                    for (x in 0 until width)
                        for (z in 0 until width)
                            field[x][y][z] = Color.WHITE;
                    return true
                }
            }
        }
        return false
    }

    fun clearField() {
        field = Array(width) {Array(height) {Array(width){Color.TRANSPARENT}}}
        curFigure = figuresBank.getFigure()
        nextFigure = figuresBank.getFigure()
        curFigure.coordinates = Point(0, 0, 0)
        nextFigure.coordinates = Point(0, 0, 0)
        gameEndTimePassed = 0.0;
        ticks = 0;
        tickColor = Color.BLACK;
        gameEnded = false;
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
        if (!isCollide(curFigure)) {
            curFigure.launchRotation(axis, direction * sign)
            return true
        }
        for (tryMoveAxis in (0..3)){
            for (dist in (1..2)){
                curFigure.move(tryMoveAxis, dist);
                if (!isCollide(curFigure)) {
                    for (i in (1..dist))
                        curFigure.launchTranslation(tryMoveAxis);
                    curFigure.launchRotation(axis, direction * sign)
                    return true
                }
                curFigure.move(tryMoveAxis, -dist);
            }
        }

        curFigure.rotate(axis, -direction)
        return false
    }

    //ось понятно, направление 1 - по оси -1 - против
    fun moveFigure(axis : Char, direction : Int, sign : Int, withTranslation : Boolean) : Boolean{
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

        if (withTranslation)
            curFigure.launchTranslation(axis, direction * sign)
        return true
    }
}

