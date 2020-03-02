package com.example.carmatic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirstActivity extends AppCompatActivity {
    EditText sUsername, sUserEmail, sUserPassword;
    Button sLoginButton, sToRegisterButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        sUsername = findViewById(R.id.username);
        sUserEmail = findViewById(R.id.userEmail);
        sUserPassword = findViewById(R.id.userPassword);
        sLoginButton = findViewById(R.id.loginButton);
        sToRegisterButton = findViewById(R.id.toRegisterButton);
        progressBar = findViewById(R.id.progressBar);


        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        sToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            }


        });

        sLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = sUserEmail.getText().toString().trim();
                String username = sUsername.getText().toString().trim();
                String password = sUserPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)){
                    sUserEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    sUserPassword.setError("Password is required");
                    return;
                }

                if (TextUtils.isEmpty(username)){
                    sUsername.setError("Username is required");

                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(FirstActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{
                            Toast.makeText(FirstActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });


            }
        });


    }
}
