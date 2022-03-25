package com.example.clownmassegefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseUser;

public class Launcher extends AppCompatActivity {

    private FirebaseUser currentUser;

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getSupportActionBar().hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getPreferences(MODE_PRIVATE);
                if (currentUser == null) {
                    Intent i = new Intent(Launcher.this, Registration.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(Launcher.this, Registration.class);
                    startActivity(i);
                }
            }
            },0*1000);
    }
}