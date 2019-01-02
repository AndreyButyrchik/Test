package com.example.test;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, GestureOverlayView.OnGesturePerformedListener {
    GestureDetectorCompat mDetector;
    TextView tv5;

    // для распознания символов
    GestureLibrary gLib;
    GestureOverlayView gestures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tv5 = (TextView) findViewById(R.id.tv5);
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);


        gLib = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if(!gLib.load()) {
            finish();
        }

        gestures = (GestureOverlayView) findViewById(R.id.gest1);
        gestures.addOnGesturePerformedListener(this);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gLib.recognize(gesture);
        if(predictions.size() > 0) {
            Prediction prediction = predictions.get(0);
            if(prediction.score > 1.0) {
                switch (prediction.name) {
                    case "2":
                        tv5.setText("2");
                        break;
                    case "3":
                        tv5.setText("3");
                        break;
                    case "+":
                        tv5.setText("+");
                        break;
                    case "-":
                        tv5.setText("-");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
//        tv5.setText(e.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
//        tv5.setText(e.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
//        tv5.setText(e.toString());
    }

    @Override
    public void onLongPress(MotionEvent e) {
//        tv5.setText(e.toString());
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
//        tv5.setText(e.toString());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        tv5.setText(e1.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        tv5.setText(e1.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
//        tv5.setText(e.toString());
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
//        tv5.setText(e.toString());
        return true;
    }
}

