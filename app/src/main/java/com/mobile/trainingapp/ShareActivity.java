package com.mobile.trainingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.trainingapp.adapter.AdapterUser;
import com.mobile.trainingapp.adapter.AdapterWorkout;
import com.mobile.trainingapp.model.Exercice;
import com.mobile.trainingapp.model.Message;
import com.mobile.trainingapp.model.User;
import com.mobile.trainingapp.model.Workout;
import com.mobile.trainingapp.ui.Friends.FriendsFragment;
import com.mobile.trainingapp.ui.Inbox.InboxFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShareActivity extends AppCompatActivity {


    private ListView listView;

    private List<User> users;
    private AdapterUser adapterUser;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefInbox = database.getReference("inbox");

    private String nomeEnviou;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        users = new ArrayList<>();

        final Workout workout = (Workout) getIntent().getExtras().get("workout");

        listView = (ListView) findViewById(R.id.idListViewFriendShare);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (workout != null && auth != null) {
                    String uidRecebeu = adapterUser.getUsers().get(position).getUid();
                    if( uidRecebeu != null ) Log.d("uidRecebeu",uidRecebeu);
                    Message message = new Message(auth.getUid(), nomeEnviou, uidRecebeu, workout.getTid());

                    shareWorkout(message);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        try {
            readUsers();
        } catch (Exception e){
            Log.i("UERR", e.getMessage());
        }
    }

    private void shareWorkout(Message message) {
        myRefInbox.push().setValue(message);
    }

    private void readUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot "+dataSnapshot.toString());
                users.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (auth.getUid().equals(snapshot.getKey())) {
                        nomeEnviou = user.getUname();
                    }

                    if (!auth.getUid().equals(snapshot.getKey())) {

                        user.setUid(snapshot.getKey());
                        Log.d("snapshot", user.toString());
                        users.add(user);
                    }
                }
                adapterUser = new AdapterUser(getApplicationContext(), users);
                listView.setAdapter(adapterUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });
    }
}