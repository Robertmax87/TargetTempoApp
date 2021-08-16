package com.example.targettempo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Login extends AppCompatActivity {
    TextInputEditText User, Password;
    TextView createNew;
    ProgressBar pBar;
    Button Login;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        User = findViewById(R.id.LoginUser);
        Password = findViewById(R.id.LoginPassword);
        pBar = findViewById(R.id.pBar);
        Login = findViewById(R.id.chapo);
        fAuth = FirebaseAuth.getInstance();
        createNew = findViewById(R.id.CreateNew);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = User.getText().toString().trim();
                String password = Password.getText().toString().trim();
                if(email.isEmpty() || User.length()<6){
                    User.setError("Username must be filled and Longer than 6 characters");
                }
                if(password.isEmpty() || Password.length() < 6){
                    Password.setError("Password must be filled out with a length longer than 6 characters");
                }
                pBar.setVisibility(View.VISIBLE);
                //Check with the database
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"User Accepted",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ScheduleInput.class));
                        }
                        else {
                            Toast.makeText(Login.this,"Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pBar.setVisibility(View.GONE);
                        }
                    }
                });

            }

        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), ActiveDashBoard.class));
            }
        });

    createNew.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), Signup.class));
        }
    });
    }

}