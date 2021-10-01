package com.example.tetris3d
import com.example.tetris3d.FiguresBank

//вроде как главный обрабатывающий класс
class Field(
    private val width : Int,
    private val height : Int
) {
    //главное поле - хранит координаты точек и их цвета
    private var figuresBank = FiguresBank()
    var curFigure = figuresBank.getFigure()
    var nextFigure = figuresBank.getFigure()

    private var field = Array(width) {Array(height) {Array(width){Color(-1, -1, -1)}}}

    fun doStep(){
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

