package com.example.clownmassegefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                        if (TextUtils.isEmpty(email.getText())) {
                            Toast.makeText(Login.this, "Введите вашу почту", Toast.LENGTH_LONG).show();
                        }
                        if (TextUtils.isEmpty(password.getText())) {
                            Toast.makeText(Login.this, "Введите пароль", Toast.LENGTH_LONG).show();
                        }
               startActivity( new Intent(Login.this, MainActivity.class));

            }
        });

        getSupportActionBar().hide();
    }

}