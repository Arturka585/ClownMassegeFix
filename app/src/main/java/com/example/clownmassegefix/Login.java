package com.example.clownmassegefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = (Button)findViewById(R.id.SingIn);
        Button singUp = (Button)findViewById(R.id.SingUp);
        EditText email = (EditText)findViewById(R.id.Email);
        EditText password = (EditText)findViewById(R.id.Password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Login.this, MainActivity.class);
                startActivity(login);
            }
        });

        getSupportActionBar().hide();
    }

}