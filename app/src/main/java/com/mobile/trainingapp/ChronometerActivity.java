package com.mobile.trainingapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.trainingapp.model.FinishedWorkout;
import com.mobile.trainingapp.model.User;
import com.mobile.trainingapp.model.Workout;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class ChronometerActivity extends AppCompatActivity  {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    private GestureDetectorCompat detectorCompat;

    private static final String TAG = "ChronometerActivity";
    private Button buttonStart;
    private Button buttonPause;
    private Button buttonFinalizarTreino;

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    private TextView cronometroSegundos;
    private TextView cronometroMinutos;
    private int seconds = 0;
    private int minutes = 0;
    private volatile boolean stopThread = false;

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("treinos_executados");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        mAuth = FirebaseAuth.getInstance();

        cronometroSegundos = findViewById(R.id.cronometroSegundos);
        cronometroMinutos = findViewById(R.id.cronometroMinutos);

        buttonFinalizarTreino = findViewById(R.id.buttonFinalizarTreino);
        buttonFinalizarTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser userId = mAuth.getCurrentUser();
                registrarTreino(userId.getUid());
                Intent intent = new Intent(ChronometerActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startThread();
            }
        });

        buttonPause = findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopThread();
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    private void registrarTreino(String userId){
        Date date = new Date();
        String dStr = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
        FinishedWorkout finishedWorkout = new FinishedWorkout();
        finishedWorkout.setDate(dStr);

        final Workout workout = (Workout) getIntent().getExtras().get("workout");
        finishedWorkout.setWorkout(workout.getTid());
        myRef.child(userId).push().setValue(finishedWorkout);
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {
                Toast.makeText(getApplicationContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
                stopThread();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    public void startThread() {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopThread() {
        stopThread = true;
    }

    class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (stopThread) return;
                Handler threadHandler = new Handler(Looper.getMainLooper());
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (seconds == 60) {
                            seconds = 0;
                            minutes++;
                        }
                        if (seconds < 10) {
                            cronometroSegundos.setText("0" + seconds);
                        } else {
                            cronometroSegundos.setText(String.valueOf(seconds));
                        }

                        if (minutes < 10) {
                            cronometroMinutos.setText("0" + minutes);
                        } else {
                            cronometroMinutos.setText(String.valueOf(minutes));
                        }
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                seconds++;
            }
        }
    }
}