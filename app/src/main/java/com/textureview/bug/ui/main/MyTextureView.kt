package com.textureview.bug.ui.main

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.TextureView
import kotlin.random.Random

class MyTextureView(context: Context, attrs: AttributeSet) : TextureView(context, attrs), TextureView.SurfaceTextureListener {
    private var surface: Surface? = null
    private var thread: RenderThread? = null

    private val debugTextPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        textSize = 64f
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        Log.d("MyTextureView", "Surface destroyed")
        this.surface = null
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        if (surface != null) {
            Log.d("MyTextureView", "Surface available")
            this.surface = Surface(surface)
        }
    }

    fun startRendering() {
        thread = RenderThread()
        thread?.start()
    }

    init {
        surfaceTextureListener = this
    }

    inner class RenderThread : Thread() {

        private var counter: Long = 0
        private var lastRun: Long = 0

        override fun run() {
            super.run()

            while (true) {

                sleep(1)

                lastRun = System.currentTimeMillis()

                if (surface != null) {
                    val canvas = surface?.lockHardwareCanvas()

                    canvas?.drawColor(Color.GRAY)

                    canvas?.drawText(
                        "HW Rendering: " + canvas.isHardwareAccelerated.toString(),
                        10f,
                        60f,
                        debugTextPaint
                    )

                    canvas?.drawText(
                        "Counter: $counter",
                        10f,
                        120f,
                        debugTextPaint
                    )

                    counter++

                    surface?.unlockCanvasAndPost(canvas)

                } else {
                    Log.d("MyTextureView", "Surface is null")
                }

            }

        }

    }


}
