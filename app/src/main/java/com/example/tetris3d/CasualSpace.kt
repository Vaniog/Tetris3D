package com.example.tetris3d

import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.customopengl.Space
import com.example.tetris3d.CustomOpenGL.ScoreBoard
import java.util.*
import kotlin.contracts.contract
import kotlin.math.*

class CasualSpace(val context: Context, val progressSpace: ProgressSpace) : Space() {
    private fun cube() {
        square(-1, 1, 1, 1, 1, 1, 1, 1, -1, -1, 1, -1)
        square(1, 1, 1, -1, 1, 1, -1, -1, 1, 1, -1, 1)
        square(-1, -1, 1, -1, -1, -1, 1, -1, -1, 1, -1, 1)
        square(-1, 1, -1, 1, 1, -1, 1, -1, -1, -1, -1, -1)
        square(-1, 1, 1, -1, 1, -1, -1, -1, -1, -1, -1, 1)
        square(1, 1, -1, 1, 1, 1, 1, -1, 1, 1, -1, -1)
    }

    private var figuresBank = FiguresBank()
    private var curFigure = figuresBank.getFigure()
    private var playFigure = curFigure.clone()

    init {
        scramble()
    }

    class Animation(var figure : Figure){
        var go = false
        var length = 0.3
        var pass = 0.0
        var changeColorSpeed = com.example.customopengl.Vector(0.0, 0.0, 0.0)
        var color = com.example.customopengl.Point(0.0, 0.0, 0.0)
        init{
            color.x = figure.color.red.toDouble()
            color.y = figure.color.green.toDouble()
            color.z = figure.color.blue.toDouble()
        }
        fun start(){
            if (go)
                return
            go = true
            pass = 0.0
            changeColorSpeed.x = figure.color.red / length
            changeColorSpeed.y = figure.color.green / length
            changeColorSpeed.z = figure.color.blue / length
            color.x = figure.color.red.toDouble()
            color.y = figure.color.green.toDouble()
            color.z = figure.color.blue.toDouble()
        }
        fun end(){
            color += changeColorSpeed * pass;
            figure.color = Color.rgb(color.x.toInt(), color.y.toInt(), color.z.toInt())
            go = false
            pass = 0.0
        }
        fun update(deltaTime : Double) : Boolean{
            if (!go)
                return false
            color -= changeColorSpeed * deltaTime;
            figure.color = Color.rgb(color.x.toInt(), color.y.toInt(), color.z.toInt())
            pass += deltaTime
            if (pass >= length){
                go = false
                return true
            }
            return false
        }
    }

    var anim1 = Animation(playFigure)
    var anim2 = Animation(curFigure)
    override fun onFrame() {
        translate(0.0, 0.0, -8.0)
        rotateZ(PI)
        rotateX(-PI / 4.0)
        rotateY(PI / 4.0)
        zoom(0.25, 0.25, 0.25)
        if (anim1.update(deltaTime) || anim2.update(deltaTime)) {
            progressSpace.scored();
            scramble()
        }
        if (curFigure.equals(playFigure))
        {
            anim1.figure = playFigure
            anim2.figure = curFigure
            anim1.start()
            anim2.start()
        }
        else{
            anim1.end()
            anim2.end()
        }
        drawFigure(playFigure)
        drawFigure(curFigure)
    }

    var mouseStartX = 0f
    var mouseStartY = 0f

    override fun onTouchEvent(event: MotionEvent?, width: Int, height: Int): Boolean {
        if (event == null)
            return true
        if (event.action == MotionEvent.ACTION_MOVE) {
            return true
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            mouseStartX = event.x
            mouseStartY = event.y
            return true
        }

        if (event.action == MotionEvent.ACTION_UP) {
            if (abs(event.y - mouseStartY) > abs(event.x - mouseStartX) && abs(event.y - mouseStartY) > height / 20.0) {
                if (mouseStartX >= width / 2) {
                    if (event.y - mouseStartY > 0)
                        rotate('z', 1)
                    else if (event.y - mouseStartY < 0)
                        rotate('z', -1)
                } else {
                    if (event.y - mouseStartY > 0)
                        rotate('x', 1)
                    else if (event.y - mouseStartY < 0)
                        rotate('x', -1)
                }
            }
            else if (abs(event.x - mouseStartX) > height / 20.0){
                rotate('y', -1 * (event.x - mouseStartX).sign.toInt())
            }
        }
        return true
    }

    fun rotate(axis : Char, direction: Int){
        playFigure.rotate(axis, direction)
        if (axis != 'x')
            playFigure.launchRotation(axis, direction)
        else
            playFigure.launchRotation(axis, -direction)
    }

    fun drawFigure(figure: Figure) {
        pushMatrix()
        val shape1 = figure.shape
        val coords1 = figure.coordinates
        val center = figure.center
        figure.updateTime(deltaTime)

        translate(
            center.x.toDouble() + coords1.x * 2.0 - 1,
            -center.y.toDouble() - coords1.y * 2.0 + 1,
            center.z.toDouble() + coords1.z * 2.0 - 1
        )
        rotateX(-figure.rotations.x)
        rotateY(-figure.rotations.y)
        rotateZ(-figure.rotations.z)
        translate(
            -center.x.toDouble() - coords1.x * 2.0 + 1,
            center.y.toDouble() + coords1.y * 2.0 - 1,
            -center.z.toDouble() - coords1.z * 2.0 + 1
        )

        val color1 = figure.color;
        color3d(color1)
        for (x in shape1.indices)
            for (y in shape1[0].indices)
                for (z in shape1[0][0].indices) {
                    if (shape1[x][y][z] == Color.TRANSPARENT)
                        continue
                    pushMatrix()
                    translate(
                        x.toDouble() * 2.0 + coords1.x * 2.0,
                        -y.toDouble() * 2.0 - coords1.y * 2.0,
                        z.toDouble() * 2.0 + coords1.z * 2.0
                    )
                    cube()
                    popMatrix()
                }
        popMatrix()
    }

    fun scramble(){
        curFigure = figuresBank.getFigure()
        playFigure = curFigure.clone()
        curFigure.scramble()
        playFigure.scramble()
        while(curFigure.equals(playFigure)){
            curFigure.scramble()
            playFigure.scramble()
        }
        curFigure.coordinates = Point((-curFigure.center.x - 1) / 2 + 2, 2, (-curFigure.center.z - 1) / 2 + 2)
        playFigure.coordinates = Point((-playFigure.center.x - 1) / 2, -3, (-playFigure.center.z - 1) / 2)
    }
}
