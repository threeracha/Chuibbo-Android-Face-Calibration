package com.example.testapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class BitMapMeshView extends View {
    /**
     * BitmapMesh의 타일 사이즈를 조정
     * */
    private int HEIGHT = 300; // mesh Height
    private int WIDTH = 300; // mesh Width
    /**
     * 사진 w, h
     * */
    private int mHeight = 900; // 사진 height
    private int mWidth = 1200; // 사진 width
    private Bitmap mbitmap; // 사진 이미지
    private int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    private float[] verts = new float[COUNT * 2]; // x0, y0, x1, y1 ......
    private float[] origs = new float[COUNT * 2]; // original 좌표
    private float k; // TODO ?....
    // TODO scope radius
    private int r = 60;
    private float startX = 0;
    private float startY = 0;
    private float moveX = 0;
    private float moveY = 0;
    private boolean showCircle = true;
    private boolean showDirection = true;
    private Paint circlePaint;
    private Paint directionPaint;

    public BitMapMeshView(Context context) {
        this(context, null);
    }

    public BitMapMeshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitMapMeshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        // Open function when using face-lift
        circlePaint = new Paint();
        circlePaint.setStrokeWidth(3);
        circlePaint.setColor(Color.parseColor("#ff0000"));
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE); // set brush painting arc (true);

        directionPaint = new Paint();
        directionPaint.setStrokeWidth(4);
        directionPaint.setColor(Color.parseColor("#ff0000"));
        directionPaint.setAntiAlias(true);

    }

    /**
     * initView()
     * 이미지 크기 설정
     * 좌표값 저장
     * */
    private void initView() {
        int index = 0;
        // 사진 resource
        mbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        // 사진 size
        mbitmap = setImgSize(mbitmap, mWidth, mHeight);
        float bitmapwidth = mbitmap.getWidth();
        float bitmapheight = mbitmap.getHeight();

        for (int i = 0; i < HEIGHT + 1; i++) {
            float fy = bitmapwidth / HEIGHT * i;
            for (int j = 0; j < WIDTH + 1; j++) {
                float fx = bitmapheight / WIDTH * j;
                // x와 y의 좌표자 연달아서 저장됨 -> x와 y의 좌표를 동시에 저장 (x: even bit, y: odd bit)
                // original, 좌표(verts) 두 군데에 동시 저장
                origs[index * 2 + 0] = verts[index * 2 + 0] = fx;
                origs[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO 이름 바꾸기
        makeBuityful(canvas);
    }

    private void makeBuityful(Canvas canvas) {
        canvas.drawBitmapMesh(mbitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        //canvas.drawBitmap(mbitmap, 300, 500, null);
        if (showCircle) {
            canvas.drawCircle(startX, startY, r, circlePaint);
        }
        if (showDirection) {
            canvas.drawLine(startX, startY, moveX, moveY, directionPaint);
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // use a face-lift, for use with makeBuityful
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Draw the deformation zone
                startX = event.getX();
                startY = event.getY();
                showCircle = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                // Draw the direction of deformation
                moveX = event.getX();
                moveY = event.getY();
                showDirection = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                showCircle = false;
                showDirection = false;
                // Call warp method to distort verts array according to the coordinates of touch screen events
                thinFace(startX, startY, event.getX(), event.getY());
                break;
        }
        return true;
    }

    public Bitmap setImgSize(Bitmap bm, int newWidth, int newHeight) {
        // get the picture width and height.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // Calculate the scaling.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // Get the matrix parameters you want to zoom.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // get new pictures.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }


    private void thinFace(float startX, float startY, float endX, float endY) {
        // Calculate the distance drag
        float ddPull = (endX - startX) * (endX - startX) + (endY - startY) * (endY - startY);
        float dPull = (float) Math.sqrt(ddPull);
        // algorithm mentioned in the literature, and can not achieve a good MC drag from the more obvious the greater the deformation effect function, this line of code is my optimization of the algorithm
        dPull = mHeight - dPull >= 0.001f ? mHeight - dPull : 0.001f;

        for (int i = 0; i < COUNT * 2; i += 2) {
            // calculate the distance between each point and the coordinates of the touch point
            float dx = verts[i] - startX;
            float dy = verts[i + 1] - startY;
            float dd = dx * dx + dy * dy;
            float d = (float) Math.sqrt(dd);

            // algorithm also mentioned in the literature can not be achieved only the image area was selected circular deformation function, where the need to make a determination of the distance
            if (d < r) {
                // deformation coefficient, twist
                double e = (r * r - dd) * (r * r - dd) / ((r * r - dd + dPull * dPull) * (r * r - dd + dPull * dPull));
                double pullX = e * (endX - startX);
                double pullY = e * (endY - startY);
                verts[i] = (float) (verts[i] + pullX);
                verts[i + 1] = (float) (verts[i + 1] + pullY);
            }
        }
        invalidate();
    }
}