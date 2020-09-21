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

import com.mobile.trainingapp.R;
import com.mobile.trainingapp.RunningActivity;
import com.mobile.trainingapp.TrainingActivity;

public class WorkoutsFragment extends Fragment {

    private WorkoutsViewModel workoutsViewModel;
    private TextView textView;
    private ListView listView;
    private String[] itens = {"Item 1", "Item 2", "Item 3", "Item 4", "a", "b", "c", "d", "e"};
    private ImageButton buttonRun;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        workoutsViewModel =
                ViewModelProviders.of(this).get(WorkoutsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_workouts, container, false);

//        textView = root.findViewById(R.id.idTextViewTeste);
        listView = (ListView) root.findViewById(R.id.idListViewStrength);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                root.getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(root.getContext(), TrainingActivity.class);
                startActivity(intent);
            }
        });

        buttonRun = root.findViewById(R.id.idButtonRun);
        buttonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), RunningActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}