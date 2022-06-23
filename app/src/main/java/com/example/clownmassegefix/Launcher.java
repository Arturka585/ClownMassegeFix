package com.example.clownmassegefix;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Launcher extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);


        FirebaseUser currentUser = FirebaseAuth.getInstance ().getCurrentUser ();
        new Handler().postDelayed(new Runnable () {
            @Override
            public void run() {
                if (currentUser != null) {
                    startActivity (new Intent (Launcher.this, MainActivity.class));
                    Launcher.this.finish ();
                } else {
                    startActivity (new Intent (Launcher.this, LoginWithEmail.class));
                    Launcher.this.finish();
                }
            }
        },1*1000);
    }
}