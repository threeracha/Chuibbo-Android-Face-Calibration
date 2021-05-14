package com.example.testapplication.opengl;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testapplication.R;

import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;

public class GLESMainActivity extends AppCompatActivity {


    private GLES30SurfaceView mGLSurfaceView;
    private GLES30FaceRenderer mGLRender = new GLES30FaceRenderer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);
        // GLES30SurfaceView 라는 클래스를 이용해서 GLSurfaceView를 초기화 할 예정.
        mGLSurfaceView = new GLES30SurfaceView(this);
        mGLSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
        setContentView(mGLSurfaceView); // mGLView 객체로 contentView를 설정해준다.
    }
}
