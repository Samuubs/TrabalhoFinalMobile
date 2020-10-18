package com.mobile.trainingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.trainingapp.adapter.AdapterUser;
import com.mobile.trainingapp.model.Message;
import com.mobile.trainingapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewFriendActivity extends AppCompatActivity {

    private ListView listView;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    private User newFriend;
    private User userLogado;

    private List<User> users;
    private AdapterUser adapterUser;

    private List<User> friends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        users = new ArrayList<>();
        friends = new ArrayList<>();

        listView = (ListView) findViewById(R.id.idListViewFriendShare);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                User user = adapterUser.getUsers().get(position);
                newFriend = new User();
                newFriend.setUid(user.getUid());
                newFriend.setUname(user.getUname());

                DatabaseReference referencef = FirebaseDatabase.getInstance().getReference("friends");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                referencef.child(auth.getUid()).child(newFriend.getUid()).setValue(newFriend);
                referencef.child(newFriend.getUid()).child(auth.getUid()).setValue(userLogado);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        readUsers();
        readUserAuth();
    }

    private void readUsers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot "+dataSnapshot.toString());

                users.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (!auth.getUid().equals(snapshot.getKey()) && !friends.contains(user)) {
                        user.setUid(snapshot.getKey());
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

    private void readUserAuth(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot "+dataSnapshot.toString());
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (auth.getUid().equals(snapshot.getKey())) {
                        userLogado = new User();
                        userLogado.setUid(snapshot.getKey());
                        userLogado.setUname(user.getUname());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });
    }

    private void readFriends() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("friends").child(auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot "+dataSnapshot.toString());
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    user.setUid(snapshot.getKey());
                    Log.d("snapshot", user.toString());
                    friends.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });
    }
}