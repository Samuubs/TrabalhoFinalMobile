package com.mobile.trainingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobile.trainingapp.model.User;

public class RegisterActivity extends AppCompatActivity {

    private Button cadastrar;
    private EditText name;
    private EditText email;
    private EditText password;

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.editTextTextName);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextNumberPassword);

        cadastrar = findViewById(R.id.cadastrarUser);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString() != "") {
                    Log.d("Btn: ", "email");
                    if (password.getText().length() > 5) {
                        Log.d("Btn: ", "senha");
                        criaUser(email.getText().toString(), password.getText().toString(), name.getText().toString());
                    } else {
                        Toast.makeText(RegisterActivity.this, "Senha com minimo de 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "E-Mail vazio", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void criaUser(final String email, final String password, final String nome) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Login: ", "createUserWithEmail:success");
                            FirebaseUser userId = mAuth.getCurrentUser();
                            User user = new User(nome, email, password);
                            gravaNome(userId.getUid(), user);
                            entrada();
                        } else {
                            Log.w("Login: ", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void gravaNome(String userId, User user){
        myRef.child(userId).setValue(user);
    }
    private void entrada() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}