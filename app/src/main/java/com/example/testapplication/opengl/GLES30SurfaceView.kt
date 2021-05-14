package com.example.testapplication.opengl

import android.content.Context
import android.opengl.GLSurfaceView

internal class GLES30SurfaceView(context: Context?) :
    GLSurfaceView(context) {
    private val mRenderer: GLES30FaceRenderer

    init {
        // OpenGL ES 3.0 context를 생성.
//        setEGLContextClientVersion(3)
        setEGLContextClientVersion(2)
        mRenderer = GLES30FaceRenderer()
        // GLSurfaceView에 렌더링 결과를 그리기 위해 현재 SurfaceView에 렌더러를 설정해준다.
        setRenderer(mRenderer)
        setRenderer(mRenderer)
    }
}