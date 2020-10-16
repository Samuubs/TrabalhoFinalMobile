package com.mobile.trainingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;

import android.gesture.Gesture;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

public class ChronometerActivity extends AppCompatActivity implements GestureDetector.OnGestureListener  {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    private GestureDetectorCompat detectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        detectorCompat = new GestureDetectorCompat(this, this);
    }
    /*
        public void startChronometer(View v) {
            if (!running) {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                running = true;
            }
        }
        public void pauseChronometer(View v) {
            if (running) {
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                running = false;
            }
        }
        public void resetChronometer(View v) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            pauseOffset = 0;
        }
    */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.detectorCompat.onTouchEvent(event))
            return true;
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;

            return true;
        } else if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;

            return true;
        }

        chronometer.stop();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}