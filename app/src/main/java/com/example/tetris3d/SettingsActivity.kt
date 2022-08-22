package com.example.tetris3d

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val darkThemeSwitch = findViewById<Switch>(R.id.darkThemeSwitch)

        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)

        darkThemeSwitch.isChecked = sharedPreferences.getBoolean("DARK_MODE_ENABLED", false)
        darkThemeSwitch.setOnClickListener{
            sharedPreferences.edit().putBoolean("DARK_MODE_ENABLED", darkThemeSwitch.isChecked).apply()
        }
    }
}