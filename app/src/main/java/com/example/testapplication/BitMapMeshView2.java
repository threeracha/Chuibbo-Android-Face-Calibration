package com.example.testapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BitMapMeshView2 extends View {

    private int screenWidth, screenHeight;
    private int mWidth, mHeight; // view 크기

    private int r = 100;

    private Paint circlePaint;
//    private Paint directionPaint;
    private boolean showCircle;
//    private boolean showDirection;

    private float startX, startY, moveX, moveY;

    // 격자
    private int WIDTH = 200;
    private int HEIGHT = 200;

    // intersection 갯수
    private int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    // 새로운 좌표 x0, y0, x1, y1
    private float[] verts = new float[COUNT * 2];
    // original 좌표
    private float[] orig = new float[COUNT * 2];

    private Bitmap mBitmap;

    private IOnStepChangeListener onStepChangeListener;

    public BitMapMeshView2(Context context) {
        super(context);
        init();
    }

    public BitMapMeshView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BitMapMeshView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);
        circlePaint.setColor(Color.parseColor("#bc2a35"));
//        directionPaint = new Paint();
//        directionPaint.setStyle(Paint.Style.FILL);
//        directionPaint.setStrokeWidth(10);
//        directionPaint.setColor(Color.parseColor("#bc2a35"));
    }

    private void initView() {
        int index = 0;
        Bitmap oriBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mBitmap = zoomBitmap(oriBitmap, mWidth, mHeight);
        float bmWidth = mBitmap.getWidth();
        float bmHeight = mBitmap.getHeight();

        for (int i = 0; i < HEIGHT + 1; i++) {
            float fy = bmHeight * i / HEIGHT;
            for (int j = 0; j < WIDTH + 1; j++) {
                float fx = bmWidth * j / WIDTH;
                // x좌표 : 짝수번째
                verts[index * 2] = fx;
                orig[index * 2] = verts[index * 2];
                // y좌표 : 홀수번째
                verts[index * 2 + 1] = fy;
                orig[index * 2 + 1] = verts[index * 2 + 1];
                index += 1;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        initView();
    }

    private Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        float scale = Math.min(scaleWidth,scaleHeight);
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        if (showCircle) {
            canvas.drawCircle(startX, startY, r, circlePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //绘制变形区域
                startX = event.getX();
                startY = event.getY();
                showCircle = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                //绘制变形方向
                moveX = event.getX();
                moveY = event.getY();
//                showDirection = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                showCircle = false;
//                showDirection = false;

                // bitmap warp
                warp(startX, startY, event.getX(), event.getY());

                onStepChangeListener.onStepChange(false);
                break;
        }
        return true;
    }

    private void warp(float startX, float startY, float endX, float endY) {

        //计算拖动距离
        float ddPull = (endX - startX) * (endX - startX) + (endY - startY) * (endY - startY);
        float dPull = (float) Math.sqrt(ddPull);
        //文献中提到的算法，并不能很好的实现拖动距离 MC 越大变形效果越明显的功能，下面这行代码则是我对该算法的优化
        dPull = screenWidth - dPull >= 0.0001f ? screenWidth - dPull : 0.0001f;

        for (int i = 0; i < COUNT * 2; i += 2) {
            //计算每个坐标点与触摸点之间的距离
            float dx = verts[i] - startX;
            float dy = verts[i + 1] - startY;
            float dd = dx * dx + dy * dy;
            float d = (float) Math.sqrt(dd);

            //文献中提到的算法同样不能实现只有圆形选区内的图像才进行变形的功能，这里需要做一个距离的判断
            if (d < r) {
                //变形系数，扭曲度
                double e = (r * r - dd) * (r * r - dd) / ((r * r - dd + dPull * dPull) * (r * r - dd + dPull * dPull));
                double pullX = e * (endX - startX);
                double pullY = e * (endY - startY);
                verts[i] = (float) (verts[i] + pullX);
                verts[i + 1] = (float) (verts[i + 1] + pullY);
            }
        }

        invalidate();
    }

    public void resetView() {
        for (int i = 0; i < verts.length; i++) {
            verts[i] = orig[i];
        }
        onStepChangeListener.onStepChange(true);
        invalidate();
    }

    public void setScreenSize(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void setOnStepChangeListener(IOnStepChangeListener onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
    }

    public interface IOnStepChangeListener {
        void onStepChange(boolean isEmpty);
    }

}