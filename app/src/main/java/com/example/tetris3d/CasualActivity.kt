package com.example.tetris3d

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import com.example.customopengl.GLSurfaceView

class CasualActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_casual)
        val menuButton = findViewById<Button>(R.id.menuButton)


        val view = GLSurfaceView(this)
        val layout = findViewById<FrameLayout>(R.id.frameLayout)

        var need : Int? = getIntent().extras?.getInt("need")
        if (need == null)
            need = -1;

        val progressSpace = ProgressSpace(this, need, this)
        val casualSpace = CasualSpace(this, progressSpace)

        view.addSpace(casualSpace)
        view.addSpace(progressSpace)
        layout.addView(view)

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