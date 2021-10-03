package com.example.tetris3d

//вроде как главный обрабатывающий класс
class Field(
    private val width : Int,
    private val height : Int
) {
    //figuresBank чтобы брать из него случайные фигуры
    private var figuresBank = FiguresBank()
    //фигура которой управляет фигура и следующая фигура
    private var curFigure = figuresBank.getFigure()
    private var nextFigure = figuresBank.getFigure()

    //главное поле - хранит координаты точек и их цвета
    private var field = Array(width) {Array(height) {Array(width){Color(-1, -1, -1)}}}

    //обновляет состояние поля
    fun doStep() : Boolean{
        curFigure.coordinates.y++

        if (isCollide(curFigure)) {
            curFigure.coordinates.y--
            addToField(curFigure)
            curFigure = nextFigure
            nextFigure = figuresBank.getFigure()
            if (isCollide(nextFigure))
                return false
        }
        return true
    }

    //проверяет не касается ли фигура клеток, и не вылезает ли за границы (но границы пока не проверяет)
    private fun isCollide(figure : Figure) : Boolean
    {
        for (x in (0 until figure.size.x))
            for (y in (0 until  figure.size.y))
                for (z in (0 until figure.size.z)) {
                    if (figure.shape[x][y][z].filled() &&
                        field[x + figure.coordinates.x][y + figure.coordinates.y][z + figure.coordinates.z].filled())
                            return true
                }
        return false
    }

    //вставляет клетки фигуры на поле, тоесть оставляет ее на поле
    private fun addToField(figure : Figure){
        for (x in (0 until figure.size.x))
            for (y in (0 until  figure.size.y))
                for (z in (0 until figure.size.z)) {
                    if (figure.shape[x][y][z].filled())
                        field[x + figure.coordinates.x][y + figure.coordinates.y][z + figure.coordinates.z] = figure.shape[x][y][z]
                }
    }

    fun stupidFill() {
        field[0][0][0] = Color(125, 125, 125)
        field[0][0][1] = Color(255, 0, 125)
        field[0][1][1] = Color(0, 0, 0)
    }

    fun clearField() {
        field = Array(width) {Array(height) {Array(width){Color(-1, -1, -1)}}}
    }

}

