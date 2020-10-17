package com.mobile.trainingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.trainingapp.adapter.AdapterExercice;
import com.mobile.trainingapp.model.Exercice;
import com.mobile.trainingapp.model.Workout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TrainingActivity extends AppCompatActivity {

    private TextView textViewName;
    private ListView listView;
    private Button startButton;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        textViewName = findViewById(R.id.textViewNameW);
        final Workout workout = (Workout) getIntent().getExtras().get("workout");
        if (workout != null) textViewName.setText(workout.getTid());

        listView = (ListView) findViewById(R.id.idListViewWorkoutExercises);
        List<Exercice> exercices = new ArrayList<>();
        for (Exercice ex: workout.getExercices().values()) {
            exercices.add(ex);
        }

        AdapterExercice adapter = new AdapterExercice(TrainingActivity.this, exercices);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String valorClicado = listView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), valorClicado, Toast.LENGTH_SHORT).show();
            }
        });

        startButton = findViewById(R.id.idButtonStartTraining);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingActivity.this, ChronometerActivity.class);
                startActivity(intent);
            }
        });


        shareButton = findViewById(R.id.buttonShare);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingActivity.this, ShareActivity.class);
                intent.putExtra("workout", (Serializable) workout);
                startActivity(intent);
            }
        });
    }


}