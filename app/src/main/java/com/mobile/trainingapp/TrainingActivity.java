package com.mobile.trainingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    private TextView textViewName;
    private ListView listView;
    private Button startButton;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        listView = (ListView) findViewById(R.id.idListViewWorkoutExercises);
        AdapterExercice adapter = new AdapterExercice(TrainingActivity.this, Mock.getInstance().mockExecice());
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

        textViewName = findViewById(R.id.textViewNameW);
        final Workout workout = (Workout) getIntent().getExtras().get("workout");
        if (workout != null) textViewName.setText(workout.getTname());

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