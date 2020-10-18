package com.mobile.trainingapp.ui.Friends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.mobile.trainingapp.NewFriendActivity;
import com.mobile.trainingapp.R;
import com.mobile.trainingapp.adapter.AdapterUser;
import com.mobile.trainingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private FriendsViewModel friendsViewModel;
    private ListView listView;
    private Button newFriendButton;

    List<User> friends;
    private AdapterUser adapterFriends;

    FirebaseAuth auth = FirebaseAuth.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        friendsViewModel =
                ViewModelProviders.of(this).get(FriendsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_friends, container, false);

        friends = new ArrayList<>();

        listView = (ListView) root.findViewById(R.id.idListViewFriends);
        newFriendButton = (Button) root.findViewById(R.id.idButtonNovoAmigo);
        newFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), NewFriendActivity.class);
                startActivity(intent);
            }
        });

        readFriends();

        return root;
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

                adapterFriends = new AdapterUser(getContext(), friends);
                listView.setAdapter(adapterFriends);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError "+databaseError.getMessage());
            }
        });

    }
}