package com.example.customopengl

import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi

var someViewCreated = false
open class GLSurfaceView (context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    var firstView = false
    private val spaces = MutableList(0){Space()}
    private var surfaceThread = SurfaceDrawingThread(holder, spaces)

    init{
        if (!someViewCreated) {
            firstView = true
            someViewCreated = true
        }
        holder.addCallback(this)
    }
    fun addSpace(space: Space){
        spaces.add(space)
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null)
            return
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        for (space in spaces)
            if (!space.stopped)
                space.fillCanvas(canvas)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        surfaceThread = SurfaceDrawingThread(holder, spaces)
        surfaceThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }


    override fun surfaceDestroyed(holder: SurfaceHolder) {
        surfaceThread.stop()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        for (space in spaces)
            space.onTouchEvent(event, width, height)
        return true
    }
}

class SurfaceDrawingThread(private val surfaceHolder: SurfaceHolder, val spaces : MutableList<Space>) : Thread(){
    private var go = true

    fun kill(){
        go = false
    }
    fun live(){
        go = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun run() {
        while(go) {

            val canvas : Canvas = surfaceHolder.lockCanvas() ?: return
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            //fps(canvas)
            for (space in spaces)
                if (!space.stopped)
                    doFrame(space, canvas)
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    var lastUpdate = 0.0
    var fpsStr = ""
    fun fps(canvas: Canvas){
        if (spaces[0].time - lastUpdate > 1.0) {
            lastUpdate = spaces[0].time
            fpsStr = (1.0 / spaces[0].deltaTime).toString()
        }
        val paint = Paint()
        paint.color = Color.WHITE
        paint.strokeWidth = 1f
        paint.textSize = 35f
        canvas.drawText(fpsStr, 0, 4, 50f, 120f, paint)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun doFrame(space: Space, canvas: Canvas){
        space.fillCanvas(canvas)
    }

}