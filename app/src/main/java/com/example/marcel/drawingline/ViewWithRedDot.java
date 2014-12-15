package com.example.marcel.drawingline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

//check website
//http://developer.android.com/training/gestures/movement.html
//http://www.mindfiresolutions.com/Using-Surface-View-for-Android-1659.php
//http://gitref.org/remotes/
/**
 * Created by marcel on 12/3/14.
 */

public class ViewWithRedDot extends View {

    public static final float TOUCH_STROKE_WIDTH = 10;

    protected int index_Array=0;
    protected Paint mPaint;
    protected Paint mPaintFinal;
    protected Bitmap mBitmap;
    protected Canvas mCanvas;
    protected Canvas inCanvas;
    protected Paint circlePaint;
    protected Paint circleRePaint;

    protected boolean stop_drawing = true;

    protected float mStartX;
    protected float mStartY;
    protected float mx;
    protected float my;


    // The sequence of indices chosen from board
    public ArrayList<Integer> sequencex = new ArrayList<Integer>();
    // The sequence of indices chosen from board
    public ArrayList<Integer> sequencey = new ArrayList<Integer>();




    public ViewWithRedDot(Context context) {
        super(context);
        init();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        inCanvas=new Canvas(mBitmap);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

            for (int initial_y = 80; initial_y <= (canvas.getHeight() - 80); initial_y += 80) {

                for (int initial_center = 80; initial_center <= (canvas.getWidth() - 80); initial_center += 80) {

                    canvas.drawCircle(canvas.getWidth() - (canvas.getWidth() - initial_center), canvas.getHeight() - (canvas.getHeight() - initial_y),
                            30, circlePaint);

                    if (index_Array == 88) {

                        stop_drawing = false;
                    }

                    if(stop_drawing) {
                        sequencex.add(canvas.getWidth() - (canvas.getWidth() - initial_center));
                        sequencey.add(canvas.getHeight() - (canvas.getHeight() - initial_y));
                        index_Array = index_Array + 1;
                    }

                }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Retrieve the point
        mx = event.getX();
        my = event.getY();


        onTouchEventLine(event);

        return true;
    }


    private void onTouchEventLine(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //First touch . Store the initial point

                for(int count2=0; count2<index_Array;count2=count2+1 ) {

                    int xtract_xxsequence=sequencex.get(count2);
                    int xtrac_yysequence=sequencey.get(count2);

                    if ((mx >= xtract_xxsequence-30) && (mx <= xtract_xxsequence+30) && ((my >= xtrac_yysequence-30) && (my <= xtrac_yysequence+30))) {

                        mStartX = xtract_xxsequence;
                        mStartY = xtrac_yysequence;
                        inCanvas.drawCircle(mStartX, mStartY, 35, circleRePaint);


                    }
                }
                invalidate();
                break;


            case MotionEvent.ACTION_MOVE:
                //We are drawing.
                    for(int count=0; count<index_Array;count=count+1 ) {

                        int xtract_xsequence=sequencex.get(count);
                        int xtrac_ysequence=sequencey.get(count);

                        if ((mx >= xtract_xsequence-30) && (mx <= xtract_xsequence+30) && ((my >= xtrac_ysequence-30) && (my <= xtrac_ysequence+30))) {
                            mCanvas.drawLine(mStartX, mStartY, xtract_xsequence, xtrac_ysequence, mPaintFinal);
                            mStartX = xtract_xsequence;
                            mStartY = xtrac_ysequence;
                            inCanvas.drawCircle(mStartX, mStartY, 35, circleRePaint);
                        }

                    }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //We are finishing the draw.

                invalidate(); //redraw
                break;
        }
    }


    //set line features
    protected void init() {

        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(TOUCH_STROKE_WIDTH);


        mPaintFinal = new Paint(Paint.DITHER_FLAG);
        mPaintFinal.setAntiAlias(true);
        mPaintFinal.setDither(true);
        mPaintFinal.setColor(getContext().getResources().getColor(android.R.color.holo_orange_dark));
        mPaintFinal.setStyle(Paint.Style.STROKE);
        mPaintFinal.setStrokeJoin(Paint.Join.ROUND);
        mPaintFinal.setStrokeCap(Paint.Cap.ROUND);
        mPaintFinal.setStrokeWidth(TOUCH_STROKE_WIDTH);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.RED);

        circleRePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        circleRePaint.setColor(Color.BLUE);
    }





}
