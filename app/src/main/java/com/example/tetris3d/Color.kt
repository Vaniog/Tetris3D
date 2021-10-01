package com.example.tetris3d
//просто цвет точки все ясно, (-1, -1, -1) - пустой
class Color(
    var r : Int,
    var g: Int,
    var b : Int
){
    //показывает не пустая ли точка
    fun filled () : Boolean{
        return r == -1
    }
}
