package com.example.targettempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
ImageButton clockButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clockButton = findViewById(R.id.imageButton3);
        clockButton.setOnClickListener(v -> {
            System.out.println("here!!!!!!!!!!!!!!");
            Intent intent = new Intent(MainActivity.this, Signup.class);
            startActivity(intent);
            finish();
        });
    }
}