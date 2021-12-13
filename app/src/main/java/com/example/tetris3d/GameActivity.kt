package com.example.tetris3d

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.customopengl.GLSurfaceView
import com.example.tetris3d.CustomOpenGL.JoystickSpace
import com.example.tetris3d.CustomOpenGL.ScoreBoard


class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        var field = Field(4, 15, 9)


        val doStep = findViewById<Button>(R.id.doStep)
        val switch = findViewById<Switch>(R.id.switch3)
        val layout = findViewById<FrameLayout>(R.id.frameLayout)
        val menuButton = findViewById<Button>(R.id.button2)


        val scoreBoard = ScoreBoard()
        val fieldSpace = FieldSpace(field, this, scoreBoard)
        //val joystickView = GLSurfaceView(this, JoystickSpace(fieldSpace))
        val view = GLSurfaceView(this)
        view.addSpace(fieldSpace)
        view.addSpace(JoystickSpace(fieldSpace))
        view.addSpace(scoreBoard)
        layout.addView(view)

        switch.setOnClickListener{
            if (switch.isChecked){
                fieldSpace.touchMode = fieldSpace.LOOK_MODE
            }
            else{
                fieldSpace.touchMode = fieldSpace.ROTATING_MODE
            }
        }

        doStep.setOnClickListener{
            val action = field.doStep(0.0)
            if(action == 0) {
                fieldSpace.onLose()
            }
            else if (action == 2)
                scoreBoard.scored()
        }

        menuButton.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}