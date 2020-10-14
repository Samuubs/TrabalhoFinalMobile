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

public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private Button buttonCadastro;
    private EditText email;
    private EditText password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        buttonLogin = findViewById(R.id.idButtonEntrar);
        email = findViewById(R.id.idEditTextLoginEmail);
        password = findViewById(R.id.idEditTextPasswordLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("aaaaa" + email.getText().toString());
                System.out.println("bbbbb" + password.getText().toString());
                entrar(email.getText().toString(), password.getText().toString());
            }
        });

        buttonCadastro = findViewById(R.id.idButtonCadastro);
        buttonCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void entrar(String email, String password){
        if (email.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, "Preencha os campos vazios.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Entrar: ", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                entrada();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Entrar: ", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void entrada(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}