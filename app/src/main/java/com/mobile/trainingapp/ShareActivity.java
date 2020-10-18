package com.mobile.trainingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.trainingapp.adapter.AdapterUser;
import com.mobile.trainingapp.model.Message;
import com.mobile.trainingapp.model.User;
import com.mobile.trainingapp.model.Workout;

import java.util.ArrayList;
import java.util.List;


public class ShareActivity extends AppCompatActivity {


    private ListView listView;

    private List<User> friends;
    private AdapterUser adapterFriend;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefInbox = database.getReference("inbox");

    private User userLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        friends = new ArrayList<>();

        final Workout workout = (Workout) getIntent().getExtras().get("workout");

        listView = (ListView) findViewById(R.id.idListViewFriendShare);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (workout != null && auth != null) {
                    String uidRecebeu = adapterFriend.getUsers().get(position).getUid();
                    if( uidRecebeu != null ) Log.d("uidRecebeu",uidRecebeu);
                    Message message = new Message(auth.getUid(), userLogado.getUname(), uidRecebeu, workout.getTid());

                    shareWorkout(message);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        try {
            readUser();
            readFriends();
        } catch (Exception e){
            Log.i("UERR", e.getMessage());
        }
    }

    private void shareWorkout(Message message) {
        myRefInbox.push().setValue(message);
    }

    private void readFriends() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("friends").child(auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot "+dataSnapshot.toString());
                friends.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    user.setUid(snapshot.getKey());
                    Log.d("snapshot", user.toString());
                    friends.add(user);
                }
                adapterFriend = new AdapterUser(getApplicationContext(), friends);
                listView.setAdapter(adapterFriend);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });
    }

    private void readUser(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot "+dataSnapshot.toString());
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (auth.getUid().equals(snapshot.getKey())) {
                       userLogado = user;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });
    }
}