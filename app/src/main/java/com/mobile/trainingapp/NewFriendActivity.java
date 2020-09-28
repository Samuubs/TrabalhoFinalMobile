package com.mobile.trainingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobile.trainingapp.ui.Friends.FriendsFragment;

public class NewFriendActivity extends AppCompatActivity {

    private Button adicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        adicionar = findViewById(R.id.adicionarFriend);
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewFriendActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}