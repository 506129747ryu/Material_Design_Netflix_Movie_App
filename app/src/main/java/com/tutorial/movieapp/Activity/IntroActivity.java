package com.tutorial.movieapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.tutorial.movieapp.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        AppCompatButton getinBtn = findViewById(R.id.getInBtn);

        getinBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, LoginActivity.class)));
    }
}
