package com.example.tetris3d
//просто цвет точки все ясно, (-1, -1, -1) - пустой
class Color(
    _r : Int,
    _g: Int,
    _b : Int
){
    var r = _r
    var g = _g
    var b = _b
    //показывает не пустая ли точка
    fun filled () : Boolean{
        return r == -1
    }
}
