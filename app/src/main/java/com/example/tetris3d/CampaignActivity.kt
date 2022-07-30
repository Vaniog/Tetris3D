package com.example.tetris3d

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class CampaignActivity : AppCompatActivity() {
    private var wasInGame = false;
    private var oldLevelsPassed = 0;
    private var levelsPassed = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        var currentTime = sharedPreferences.getLong("CAMPAIGN_CURRENT_TIME", 0)
        levelsPassed = sharedPreferences.getInt("CAMPAIGN_LEVELS_PASSED", 0)
        oldLevelsPassed = levelsPassed;
        var bestTime = sharedPreferences.getFloat("CAMPAIGN_BEST_TIME", -1f)
        var startTime = System.currentTimeMillis();
        setContentView(R.layout.activity_campaign)

        val classicButton1 = findViewById<Button>(R.id.classicButton1)
        val classicButton2 = findViewById<Button>(R.id.classicButton2)
        val classicButton3 = findViewById<Button>(R.id.classicButton3)
        val casualButton1 = findViewById<Button>(R.id.casualButton1)
        val casualButton2 = findViewById<Button>(R.id.casualButton2)
        val casualButton3 = findViewById<Button>(R.id.casualButton3)
        val menuButton = findViewById<Button>(R.id.menuButton2)
        val restartButton = findViewById<Button>(R.id.restartButton)
        menuButton.setOnClickListener{
            finish()
        }
        restartButton.setOnClickListener(){
            sharedPreferences.edit().putInt("CAMPAIGN_LEVELS_PASSED", 0).apply()
            sharedPreferences.edit().putLong("CAMPAIGN_CURRENT_TIME", 0).apply()
            this.onResume()
        }


        fun onGameLaunch() {
            startTime = System.currentTimeMillis()
            sharedPreferences.edit().putLong("CAMPAIGN_START_TIME", startTime).apply()
            wasInGame = true;
        }
        casualButton1.setOnClickListener{
            if (levelsPassed == 0) {
                onGameLaunch()
                val i = Intent(this, CasualActivity::class.java)
                i.putExtra("need", 10)
                startActivity(i)
            }
        }
        classicButton1.setOnClickListener{
            if (levelsPassed == 1) {
                onGameLaunch()
                val i = Intent(this, GameActivity::class.java)
                i.putExtra("need", 5)
                startActivity(i)
            }
        }
        casualButton2.setOnClickListener{
            if (levelsPassed == 2) {
                onGameLaunch()
                val i = Intent(this, CasualActivity::class.java)
                i.putExtra("need", 20)
                startActivity(i)
            }
        }
        classicButton2.setOnClickListener{
            if (levelsPassed == 3) {
                onGameLaunch()
                val i = Intent(this, GameActivity::class.java)
                i.putExtra("need", 10)
                startActivity(i)
            }
        }
        casualButton3.setOnClickListener{
            if (levelsPassed == 4) {
                onGameLaunch()
                val i = Intent(this, CasualActivity::class.java)
                i.putExtra("need", 30)
                startActivity(i)
            }
        }
        classicButton3.setOnClickListener{
            if (levelsPassed == 5) {
                onGameLaunch()
                val i = Intent(this, GameActivity::class.java)
                i.putExtra("need", 15)
                startActivity(i)
            }
        }
    }

    fun colorButtons(levelsPassed : Int){
        val classicButton1 = findViewById<Button>(R.id.classicButton1)
        val classicButton2 = findViewById<Button>(R.id.classicButton2)
        val classicButton3 = findViewById<Button>(R.id.classicButton3)
        val casualButton1 = findViewById<Button>(R.id.casualButton1)
        val casualButton2 = findViewById<Button>(R.id.casualButton2)
        val casualButton3 = findViewById<Button>(R.id.casualButton3)
        classicButton1.setTextColor(Color.RED);
        classicButton2.setTextColor(Color.RED);
        classicButton3.setTextColor(Color.RED);
        casualButton1.setTextColor(Color.RED);
        casualButton2.setTextColor(Color.RED);
        casualButton3.setTextColor(Color.RED);

        if (levelsPassed == 0){
            casualButton1.setTextColor(Color.YELLOW)
        }
        if (levelsPassed == 1){
            casualButton1.setTextColor(Color.GREEN)
            classicButton1.setTextColor(Color.YELLOW)
        }
        if (levelsPassed == 2){
            casualButton1.setTextColor(Color.GREEN)
            classicButton1.setTextColor(Color.GREEN)
            casualButton2.setTextColor(Color.YELLOW)
        }
        if (levelsPassed == 3){
            casualButton1.setTextColor(Color.GREEN)
            classicButton1.setTextColor(Color.GREEN)
            casualButton2.setTextColor(Color.GREEN)
            classicButton2.setTextColor(Color.YELLOW)
        }
        if (levelsPassed == 4){
            casualButton1.setTextColor(Color.GREEN)
            classicButton1.setTextColor(Color.GREEN)
            casualButton2.setTextColor(Color.GREEN)
            classicButton2.setTextColor(Color.GREEN)
            casualButton3.setTextColor(Color.YELLOW)
        }
        if (levelsPassed == 5){
            casualButton1.setTextColor(Color.GREEN)
            classicButton1.setTextColor(Color.GREEN)
            casualButton2.setTextColor(Color.GREEN)
            classicButton2.setTextColor(Color.GREEN)
            casualButton3.setTextColor(Color.GREEN)
            classicButton3.setTextColor(Color.YELLOW)
        }
        if (levelsPassed == 6){
            casualButton1.setTextColor(Color.GREEN)
            classicButton1.setTextColor(Color.GREEN)
            casualButton2.setTextColor(Color.GREEN)
            classicButton2.setTextColor(Color.GREEN)
            casualButton3.setTextColor(Color.GREEN)
            classicButton3.setTextColor(Color.GREEN)
        }
    }

    override fun onResume() {
        wasInGame = false
        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        var currentTime = sharedPreferences.getLong("CAMPAIGN_CURRENT_TIME", 0) / 1000f
        levelsPassed = sharedPreferences.getInt("CAMPAIGN_LEVELS_PASSED", 0)
        var bestTime = sharedPreferences.getFloat("CAMPAIGN_BEST_TIME", -1f)
        val textView = findViewById<TextView>(R.id.timeText)

        colorButtons(levelsPassed)

        var str : String = "Current time : $currentTime"


        if (levelsPassed == 6)
        {
            if (currentTime < bestTime || bestTime == -1f) {
                bestTime = currentTime
                sharedPreferences.edit()
                    .putFloat("CAMPAIGN_BEST_TIME", bestTime).apply()
            }
            str = "Current pass time : $currentTime\nBest time : $bestTime";
        }

        textView.text = str;

        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }
}