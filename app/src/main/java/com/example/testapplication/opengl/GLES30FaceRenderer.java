package com.example.testapplication.opengl;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLES30FaceRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "GLES30FaceRenderer";
    private GLES30JNILib nativeRenderer;
    private int mSampleType;

    GLES30FaceRenderer() { nativeRenderer = new GLES30JNILib();}

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        nativeRenderer.native_OnSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        nativeRenderer.native_OnSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        nativeRenderer.native_OnDrawFrame();
    }

    public void init() {
        nativeRenderer.native_Init();
    }

    public void unInit() {
        nativeRenderer.native_UnInit();
    }

//    public void setParamsInt(int paramType, int value0, int value1) {
//        if (paramType == SAMPLE_TYPE) {
//            mSampleType = value0;
//        }
//        nativeRenderer.native_SetParamsInt(paramType, value0, value1);
//    }

//    public void setTouchLoc(float x, float y)
//    {
//        nativeRenderer.native_SetParamsFloat(SAMPLE_TYPE_SET_TOUCH_LOC, x, y);
//    }
//
//    public void setGravityXY(float x, float y) {
//        nativeRenderer.native_SetParamsFloat(SAMPLE_TYPE_SET_GRAVITY_XY, x, y);
//    }

    public void setImageData(int format, int width, int height, byte[] bytes) {
        nativeRenderer.native_SetImageData(format, width, height, bytes);
    }

    public void setImageDataWithIndex(int index, int format, int width, int height, byte[] bytes) {
        nativeRenderer.native_SetImageDataWithIndex(index, format, width, height, bytes);
    }

    public int getSampleType() {
        return mSampleType;
    }

    public void updateTransformMatrix(float rotateX, float rotateY, float scaleX, float scaleY)
    {
        nativeRenderer.native_UpdateTransformMatrix(rotateX, rotateY, scaleX, scaleY);
    }

}