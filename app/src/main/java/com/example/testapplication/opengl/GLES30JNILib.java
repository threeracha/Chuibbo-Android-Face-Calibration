package com.example.testapplication.opengl;

import android.graphics.Bitmap;

public class GLES30JNILib {

    static {
//        System.loadLibrary("gles30jni");
//        System.loadLibrary("native-render");
        // TODO faceSlender.cpp는 다른 라이브러리로 분리시키
        System.load("native-lib");
    }

    public native void native_Init();

    public native void native_UnInit();

    public native void native_SetParamsInt(int paramType, int value0, int value1);

    public native void native_SetParamsFloat(int paramType, float value0, float value1);

    public native void native_UpdateTransformMatrix(float rotateX, float rotateY, float scaleX, float scaleY);

    public native void native_SetImageData(int format, int width, int height, byte[] bytes);

    public native void native_SetImageDataWithIndex(int index, int format, int width, int height, byte[] bytes);

    public native void native_OnSurfaceCreated();

    public native void native_OnSurfaceChanged(int width, int height);

    public native void native_OnDrawFrame();


//    public native void LoadImage();
//
//    public native void LoadMultiImageWithIndex(int index, Bitmap bitmap);
//
//    public native void LoadShortArrData(short *const pShortArr, int arrSize);
//
//    public native void UpdateTransformMatrix(float rotateX, float rotateY, float scaleX, float scaleY)
//
//    public native void SetTouchLocation(float x, float y);
//
//    public native void SetGravityXY(float x, float y);
//
//    public native void Init();
//    public native void Draw(int screenW, int screenH);
//
//    public native void Destroy();

}
