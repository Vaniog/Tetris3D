package com.example.tetris3d

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.customopengl.GLSurfaceView
import com.example.customopengl.Space
import com.example.tetris3d.CustomOpenGL.JoystickSpace
import kotlin.math.PI

class TestSpace() : Space(){
    private fun cube(){
        square(-1, 1, 1, 1, 1, 1, 1, 1, -1, -1, 1, -1)
        square(1, 1, 1, -1, 1, 1, -1, -1, 1, 1, -1, 1)
        square(1, -1, 1, -1, -1, 1, -1, -1, -1, 1, -1, -1)
        square(-1, 1, -1, 1, 1, -1, 1, -1, -1, -1, -1, -1)
        square(-1, 1, 1, -1, 1, -1, -1, -1, -1, -1, -1, 1)
        square(1, 1, -1, 1, 1, 1, 1, -1, 1, 1, -1, -1)
    }
    override fun onFrame() {
        color3d(255, 0, 255)
        rotateY(PI)
        translate(0.0, 0.0, 20.0)
        rotateX(-PI/4.0)
        rotateY(time)
        cube()
    }
}



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var field = Field(4, 15)
        field.stupidFill()


        val doStep = findViewById<Button>(R.id.doStep)
        val layout = findViewById<FrameLayout>(R.id.frameLayout)

        doStep.setOnClickListener{
            if(!field.doStep())
                field.clearField()
        }

        val fieldSpace = FieldSpace(field, this)
        //val joystickView = GLSurfaceView(this, JoystickSpace(fieldSpace))
        val view = GLSurfaceView(this)
        view.addSpace(fieldSpace)
        view.addSpace(JoystickSpace(fieldSpace))
        layout.addView(view)

    }
}