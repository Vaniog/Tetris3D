package com.example.tetris3d

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
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


        var need : Int? = getIntent().extras?.getInt("need")
        if (need == null)
            need = -1;

        val scoreBoard = ScoreBoard(need, this)
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
            var action = field.doStep(true)
            while (action == 3) {
                if (action == 0) {
                    fieldSpace.onLose()
                } else if (action == 2)
                    scoreBoard.scored()
                action = field.doStep(true)
            }
        }



        menuButton.setOnClickListener{
            finish()
        }
    }
    var currentTime = 0L
    fun updateCampaignTime(){
        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        currentTime = sharedPreferences.getLong("CAMPAIGN_CURRENT_TIME", 0)
        var startTime = sharedPreferences.getLong("CAMPAIGN_START_TIME", System.currentTimeMillis())
        currentTime += System.currentTimeMillis() - startTime
        startTime = System.currentTimeMillis()
        sharedPreferences.edit().putLong("CAMPAIGN_START_TIME", startTime).apply()
        sharedPreferences.edit().putLong("CAMPAIGN_CURRENT_TIME", currentTime).apply()
    }
    fun levelPassed(){
        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        var levelsPassed = sharedPreferences.getInt("CAMPAIGN_LEVELS_PASSED", 0)
        levelsPassed++
        sharedPreferences.edit().putInt("CAMPAIGN_LEVELS_PASSED", levelsPassed).apply()
    }
}