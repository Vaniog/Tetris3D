package com.example.tetris3d

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("CommitPrefEdits", "SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val highScoreText = findViewById<TextView>(R.id.textView2)
        val classicButton = findViewById<Button>(R.id.classicButton)
        val casualButton = findViewById<Button>(R.id.casualButton)
        val campaignButton = findViewById<Button>(R.id.campaignButton)
        val tutorialButton = findViewById<Button>(R.id.tutorialButton)

        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        val highScore = sharedPreferences.getInt("HIGH_SCORE", 0)
        highScoreText.text = "High Score : $highScore"

        classicButton.setOnClickListener{
            val i = Intent(this, GameActivity::class.java)
            startActivity(i)
        }

        casualButton.setOnClickListener{
            val i = Intent(this, CasualActivity::class.java)
            startActivity(i)
        }
        campaignButton.setOnClickListener{
            val i = Intent(this, CampaignActivity::class.java)
            startActivity(i)
        }
        tutorialButton.setOnClickListener{
            val i = Intent(this, TutorialActivity::class.java)
            startActivity(i)
        }
    }
}