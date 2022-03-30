package com.example.clownmassegefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        FirebaseAuth currentUser = FirebaseAuth.getInstance ();
        currentUser.getCurrentUser ();

        new Handler().postDelayed(new Runnable () {
            @Override
            public void run() {
                if (currentUser != null) {
                    Intent i = new Intent (Launcher.this, MainActivity.class);
                    Launcher.this.startActivity (i);
                    Launcher.this.finish ();
                } else {
                    Intent i = new Intent (Launcher.this, Login.class);
                    Launcher.this.startActivity (i);
                }
            }
        },0*1000);
    }
}