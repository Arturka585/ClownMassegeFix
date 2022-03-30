package com.example.clownmassegefix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login = findViewById(R.id.SingIn);
        Button singUp = findViewById(R.id.Register);
        EditText email = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);
        FirebaseAuth Authentication;

        Authentication = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, MainActivity.class));

                if (TextUtils.isEmpty(email.getText())) {
                    Toast.makeText(Login.this, "Введите вашу почту", Toast.LENGTH_LONG).show();
                }
                if (password.getText().length() < 6) {
                    Toast.makeText(Login.this, "Введите пароль, состоящий из 6 или более символов", Toast.LENGTH_LONG).show();
                }
                Authentication.signInWithEmailAndPassword (email.getText ().toString ().trim (),password.getText ().toString ().trim ()).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful ()){
                         Toast.makeText (Login.this, "Вы успешно авторизировались!", Toast.LENGTH_SHORT).show ();
                         startActivity (new Intent (Login.this,MainActivity.class));
                         Login.this.finish ();
                     }else {
                         Toast.makeText (Login.this, "Вы не авторизировались, обратитесь в поддержку!!", Toast.LENGTH_SHORT).show ();
                     }
                    }
                });

            }
        });

        singUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Login.this.startActivity (new Intent (Login.this, Registration.class));
                Login.this.finish ();
            }
        });
    }

}