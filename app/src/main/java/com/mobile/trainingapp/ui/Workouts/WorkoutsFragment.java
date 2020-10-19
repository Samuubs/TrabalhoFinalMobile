package com.mobile.trainingapp.ui.Workouts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.trainingapp.R;
import com.mobile.trainingapp.RunningActivity;
import com.mobile.trainingapp.TrainingActivity;
import com.mobile.trainingapp.adapter.AdapterWorkout;
import com.mobile.trainingapp.model.Workout;
import com.mobile.trainingapp.utils.RecyclerItemClickListener;

import java.util.ArrayList;
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
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mWorkouts = new ArrayList<>();
        readWorkouts();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getContext(), TrainingActivity.class);
                                intent.putExtra("workout", adapterWorkout.getmWorkouts().get(position));
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        })
        );
    }

    private void readWorkouts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("treinos");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot "+dataSnapshot.toString());
                mWorkouts.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Log.d("snapshot", snapshot.toString());
                    Workout workout = snapshot.getValue(Workout.class);
                    mWorkouts.add(workout);
                }

                adapterWorkout = new AdapterWorkout(getContext(), mWorkouts);
                recyclerView.setAdapter(adapterWorkout);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });
    }
}