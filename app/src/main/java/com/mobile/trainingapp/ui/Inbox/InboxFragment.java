package com.mobile.trainingapp.ui.Inbox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.trainingapp.ChronometerActivity;
import com.mobile.trainingapp.Mock;
import com.mobile.trainingapp.R;
import com.mobile.trainingapp.adapter.AdapterMessage;
import com.mobile.trainingapp.adapter.AdapterWorkout;
import com.mobile.trainingapp.model.Message;
import com.mobile.trainingapp.model.Workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        AdapterMessage adapter = new AdapterMessage(root.getContext(), Mock.getInstance().getMessages());
        listViewMessages.setAdapter(adapter);

        listViewMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(root.getContext(), ChronometerActivity.class);
                startActivity(intent);
            }
        });

        messages = new ArrayList<>();

        readMessages();

        return root;
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

                adapterMessage = new AdapterMessage(getContext(), messages);
                listViewMessages.setAdapter(adapterMessage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });
    }
}