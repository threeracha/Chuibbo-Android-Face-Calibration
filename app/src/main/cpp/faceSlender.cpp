//
// Created by 강지연 on 5/10/21.
//

#include "../../../../../../Library/Android/sdk/ndk/21.1.6352462/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/jni.h"
//#include <gtc/matrix_transform.hpp>
//#include "../util/GLUtils.h"
//#include "CommonDef.h"

float LeftCheekKeyPoint[] = {211, 363};//左脸颊关键点
float ChinKeyPoint[] = {336, 565};//下巴关键点
float RightCheekPoint[] = {471, 365};//右脸颊关键点
float LeftSlenderCtlPoint[] = {211, 512};//左侧控制点
float RightSlenderCtlPoint[] = {477, 509};//右侧控制点

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1Init(JNIEnv *env, jobject thiz) {
// TODO: implement native_Init()

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1UnInit(JNIEnv *env, jobject thiz) {
    // TODO: implement native_UnInit()
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1SetParamsInt(JNIEnv *env, jobject thiz,
                                                                          jint param_type,
                                                                          jint value0,
                                                                          jint value1) {
    // TODO: implement native_SetParamsInt()
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1SetParamsFloat(JNIEnv *env,
                                                                            jobject thiz,
                                                                            jint param_type,
                                                                            jfloat value0,
                                                                            jfloat value1) {
    // TODO: implement native_SetParamsFloat()
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1UpdateTransformMatrix(JNIEnv *env,
                                                                                   jobject thiz,
                                                                                   jfloat rotate_x,
                                                                                   jfloat rotate_y,
                                                                                   jfloat scale_x,
                                                                                   jfloat scale_y) {
    // TODO: implement native_UpdateTransformMatrix()
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1SetImageData(JNIEnv *env, jobject thiz,
                                                                          jint format, jint width,
                                                                          jint height,
                                                                          jbyteArray bytes) {
    // TODO: implement native_SetImageData()
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1SetImageDataWithIndex(JNIEnv *env,
                                                                                   jobject thiz,
                                                                                   jint index,
                                                                                   jint format,
                                                                                   jint width,
                                                                                   jint height,
                                                                                   jbyteArray bytes) {
    // TODO: implement native_SetImageDataWithIndex()
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1OnSurfaceCreated(JNIEnv *env,
                                                                              jobject thiz) {
    // TODO: implement native_OnSurfaceCreated()
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1OnSurfaceChanged(JNIEnv *env,
                                                                              jobject thiz,
                                                                              jint width,
                                                                              jint height) {
    // TODO: implement native_OnSurfaceChanged()
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_testapplication_opengl_GLES30JNILib_native_1OnDrawFrame(JNIEnv *env,
                                                                         jobject thiz) {
    // TODO: implement native_OnDrawFrame()
}