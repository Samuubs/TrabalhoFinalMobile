package com.mobile.trainingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobile.trainingapp.adapter.AdapterUser;
import com.mobile.trainingapp.model.Message;
import com.mobile.trainingapp.model.User;
import com.mobile.trainingapp.model.Workout;
import com.mobile.trainingapp.ui.Friends.FriendsFragment;
import com.mobile.trainingapp.ui.Inbox.InboxFragment;


public class ShareActivity extends AppCompatActivity {


    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        listView = (ListView) findViewById(R.id.idListViewFriendShare);
        AdapterUser adapter = new AdapterUser( ShareActivity.this, Mock.getInstance().mockUsers());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Workout workout = (Workout) getIntent().getExtras().get("workout");
                User user = Mock.getInstance().mockUsers().get(position);
                Message message = new Message();
                message.setUser(user);
                message.setWorkout(workout);
                Mock.getInstance().getMessages().add(message);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}