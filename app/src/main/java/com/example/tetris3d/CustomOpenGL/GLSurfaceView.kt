package com.example.customopengl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi

var someViewCreated = false
open class GLSurfaceView (context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    var firstView = false
    private val spaces = MutableList(0){Space()}
    private var surfaceThread = SurfaceDrawingThread(holder, spaces, firstView)

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

    override fun surfaceCreated(holder: SurfaceHolder) {
        surfaceThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (!surfaceThread.isAlive)
            surfaceThread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        for (space in spaces)
            space.onTouchEvent(event, width, height)
        return true
    }
}

class SurfaceDrawingThread(private val surfaceHolder: SurfaceHolder, private val spaces : MutableList<Space>, val firstView : Boolean) : Thread(){
    private var go = true

    fun kill(){
        go = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun run() {
        while(go) {
            val canvas : Canvas = surfaceHolder.lockCanvas() ?: return
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            for (space in spaces)
                if (!space.stopped)
                    doFrame(space, canvas)
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun doFrame(space: Space, canvas: Canvas){
        space.fillCanvas(canvas)
    }

}