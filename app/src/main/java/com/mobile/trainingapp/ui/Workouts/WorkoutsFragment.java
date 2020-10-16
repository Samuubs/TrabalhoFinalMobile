package com.mobile.trainingapp.ui.Workouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.trainingapp.Mock;
import com.mobile.trainingapp.R;
import com.mobile.trainingapp.RunningActivity;
import com.mobile.trainingapp.TrainingActivity;
import com.mobile.trainingapp.adapter.AdapterWorkout;
import com.mobile.trainingapp.model.Exercice;
import com.mobile.trainingapp.model.User;
import com.mobile.trainingapp.model.Workout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkoutsFragment extends Fragment {

    private WorkoutsViewModel workoutsViewModel;
    private TextView textView;
    private RecyclerView recyclerView;
    private ImageButton buttonRun;

    private List<Workout> mWorkouts;
    private AdapterWorkout adapterWorkout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        workoutsViewModel =
                ViewModelProviders.of(this).get(WorkoutsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_workouts, container, false);

        buttonRun = root.findViewById(R.id.idButtonRun);
        buttonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), RunningActivity.class);
                startActivity(intent);
            }
        });

        recyclerView= root.findViewById(R.id.idListViewStrength);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mWorkouts = new ArrayList<>();
        readWorkouts();

        return root;

//        listView = (ListView) root.findViewById(R.id.idListViewStrength);
//        AdapterWorkout adapter = new AdapterWorkout(root.getContext(), Mock.getInstance().mockWorkouts());
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent intent = new Intent(root.getContext(), TrainingActivity.class);
//                intent.putExtra("workout", Mock.getInstance().mockWorkouts().get(position));
//                startActivity(intent);
//            }
//        });
    }

    private void readWorkouts() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("treinos");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mWorkouts.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    System.out.println("snapshot" + snapshot.toString());
                    Workout workout = new Workout();
                    workout.setTid(snapshot.getKey());
                    workout.setExercices((HashMap<String, Exercice>) snapshot.getValue());
                    assert  workout != null;
                    assert firebaseUser != null;
                    mWorkouts.add(workout);
                }

                adapterWorkout = new AdapterWorkout(getContext(), mWorkouts);
                recyclerView.setAdapter(adapterWorkout);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}