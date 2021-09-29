package com.example.tetris3d

//вроде как главный обрабатывающий класс
class Field(
    _width : Int,
    _height : Int
) {
    //главное поле - хранит координаты точек и их цвета
    val width = _width
    val height = _height

    private var field = Array(width) {Array(height) {Array(width){Color(-1, -1, -1)} }}
    fun stupidFill()
    {
        field[0][0][0] = Color(125, 125, 125)
        field[0][0][1] = Color(255, 0, 125)
        field[0][1][1] = Color(0, 0, 0)
    }

    fun clearField()
    {
        field = Array(width) {Array(height) {Array(width){Color(-1, -1, -1)}}}
    }
}

