package com.example.tetris3d

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
        val progressSpace = ProgressSpace(this)
        val casualSpace = CasualSpace(this, progressSpace)

        view.addSpace(casualSpace)
        view.addSpace(progressSpace)
        layout.addView(view)

        menuButton.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}