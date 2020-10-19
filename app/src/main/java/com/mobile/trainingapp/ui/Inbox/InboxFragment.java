package com.mobile.trainingapp.ui.Inbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.trainingapp.R;
import com.mobile.trainingapp.TrainingActivity;
import com.mobile.trainingapp.adapter.AdapterMessage;
import com.mobile.trainingapp.model.Message;
import com.mobile.trainingapp.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment {

    private InboxViewModel inboxViewModel;
    private ListView listViewMessages;

    private List<Message> messages;
    private AdapterMessage adapterMessage;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inboxViewModel =
                ViewModelProviders.of(this).get(InboxViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_inbox, container, false);

        listViewMessages = root.findViewById(R.id.idListViewInbox);


        listViewMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                goToWorkout(messages.get(position).getWorkout());
            }
        });

        messages = new ArrayList<>();

        readMessages();

        return root;
    }

    public void goToWorkout(String workout) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("treinos/" + workout);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Workout workout = dataSnapshot.getValue(Workout.class);
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                intent.putExtra("workout", workout);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });
    }

    private void readMessages() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("inbox");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot "+dataSnapshot.toString());
                messages.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Log.d("snapshot", snapshot.toString());
                    Message message = snapshot.getValue(Message.class);
                    if (message.getUidRecebeu().equals(auth.getUid())) messages.add(message);
                }

                if (getActivity() != null) {
                    adapterMessage = new AdapterMessage(getContext(), messages);
                    listViewMessages.setAdapter(adapterMessage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });
    }
}