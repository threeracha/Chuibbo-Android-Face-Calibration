package com.example.testapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity

class FaceMeshActivity : AppCompatActivity() {
    var imageView: ImageView? = null
    var seekbar_skewx: SeekBar? = null
    var seekbar_skewy: SeekBar? = null
    private var curSkewX = 0f
    private var curSkewY = 0f
    var bitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facemesh)
        imageView = findViewById<View>(R.id.face_mesh_img) as ImageView
        seekbar_skewx = findViewById(R.id.skew_x)
        seekbar_skewx?.setProgress(50, true)
        seekbar_skewy = findViewById(R.id.skew_y)
        seekbar_skewy?.setProgress(50, true)
        seekbar_skewx?.setOnSeekBarChangeListener(seekbarSkewXChangeListener)
        seekbar_skewy?.setOnSeekBarChangeListener(seekbarSkewYChangeListener)
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.test)
        imageView!!.setImageBitmap(bitmap)
    }

    private fun MatrixX() {
        val m = Matrix()
        val bw = bitmap!!.width
        val bh = bitmap!!.height
        val oldw = bitmap!!.width
        val oldh = bitmap!!.height
        val src = RectF(0F, 0F, bw.toFloat(), bh.toFloat())
        val dst = RectF(0F, 0F, oldw.toFloat(), oldh.toFloat())
        m.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER)
        val pts = floatArrayOf(
            0f,
            0f,
            0f,
            bh.toFloat(),
            bw.toFloat(),
            bh.toFloat(),
            bw.toFloat(),
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f
        )
        m.mapPoints(pts, 8, pts, 0, 4)
        val DX = curSkewX.toInt()
        pts[8] += DX.toFloat()
        pts[14] -= DX.toFloat()
        m.setPolyToPoly(pts, 0, pts, 8, 4)
        val bM = Bitmap.createBitmap(
            bitmap!!, 0, 0,
            bitmap!!.width, bitmap!!.height,
            m, true
        )
        imageView!!.setImageBitmap(bM)
    }
    private fun ReverseMatrixX() {
        val m = Matrix()
        val bw = bitmap!!.width
        val bh = bitmap!!.height
        val oldw = bitmap!!.width
        val oldh = bitmap!!.height
        val src = RectF(0F, 0F, bw.toFloat(), bh.toFloat())
        val dst = RectF(0F, 0F, oldw.toFloat(), oldh.toFloat())
        m.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER)
        val pts = floatArrayOf(
            0f,
            0f,
            0f,
            bh.toFloat(),
            bw.toFloat(),
            bh.toFloat(),
            bw.toFloat(),
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f
        )
        m.mapPoints(pts, 8, pts, 0, 4)
        val DX = curSkewX.toInt()
        pts[8] -= DX.toFloat()
        pts[14] += DX.toFloat()
        m.setPolyToPoly(pts, 0, pts, 8, 4)
        val bM = Bitmap.createBitmap(
            bitmap!!, 0, 0,
            bitmap!!.width, bitmap!!.height,
            m, true
        )
        imageView!!.setImageBitmap(bM)
    }

    private fun MatrixY() {
        val m = Matrix()
        val bw = bitmap!!.width
        val bh = bitmap!!.height
        val oldw = bitmap!!.width
        val oldh = bitmap!!.height
        val src = RectF(0F, 0F, bw.toFloat(), bh.toFloat())
        val dst = RectF(0F, 0F, oldw.toFloat(), oldh.toFloat())
        m.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER)
        val pts = floatArrayOf(
            0f,
            0f,
            0f,
            bh.toFloat(),
            bw.toFloat(),
            bh.toFloat(),
            bw.toFloat(),
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f
        )
        m.mapPoints(pts, 8, pts, 0, 4)
        val DX = curSkewY.toInt()
        pts[15] += DX.toFloat()
        pts[13] -= DX.toFloat()
        m.setPolyToPoly(pts, 0, pts, 8, 4)
        val bM = Bitmap.createBitmap(
            bitmap!!, 0, 0,
            bitmap!!.width, bitmap!!.height,
            m, true
        )
        imageView!!.setImageBitmap(bM)
    }
    private fun ReverseMatrixY() {
        val m = Matrix()
        val bw = bitmap!!.width
        val bh = bitmap!!.height
        val oldw = bitmap!!.width
        val oldh = bitmap!!.height
        val src = RectF(0F, 0F, bw.toFloat(), bh.toFloat())
        val dst = RectF(0F, 0F, oldw.toFloat(), oldh.toFloat())
        m.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER)
        val pts = floatArrayOf(
            0f,
            0f,
            0f,
            bh.toFloat(),
            bw.toFloat(),
            bh.toFloat(),
            bw.toFloat(),
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f,
            0f
        )
        m.mapPoints(pts, 8, pts, 0, 4)
        val DX = curSkewY.toInt()
        pts[11] -= DX.toFloat()
        pts[9] += DX.toFloat()
        m.setPolyToPoly(pts, 0, pts, 8, 4)
        val bM = Bitmap.createBitmap(
            bitmap!!, 0, 0,
            bitmap!!.width, bitmap!!.height,
            m, true
        )
        imageView!!.setImageBitmap(bM)
    }

    private val seekbarSkewXChangeListener: OnSeekBarChangeListener =
        object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.d("test", "onProgressChanged: $progress")
                if ( progress >= 50) {
                    curSkewX = progress.toFloat()
                    MatrixX()
                } else {
                    curSkewX = 100 - progress.toFloat()
                    ReverseMatrixX()
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }
    private val seekbarSkewYChangeListener: OnSeekBarChangeListener =
        object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if ( progress >= 50) {
                    curSkewY = progress.toFloat()
                    MatrixY()
                } else {
                    curSkewY = 100 - progress.toFloat()
                    ReverseMatrixY()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }
}