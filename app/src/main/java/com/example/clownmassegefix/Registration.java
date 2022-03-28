package com.example.clownmassegefix;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        Button singUp = findViewById (R.id.Register);
        Button singIn = findViewById (R.id.SingIn);
        EditText userName = findViewById (R.id.UserName);
        EditText mEmail = findViewById (R.id.Email);
        EditText mPassword = findViewById (R.id.Password);
        EditText mConfirmPassword = findViewById (R.id.Confirm_Password);
        FirebaseAuth Authentication;

        Authentication = FirebaseAuth.getInstance();

        getSupportActionBar().hide();


        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEmail.getText())) {
                    Toast.makeText(Registration.this, "Введите вашу почту", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(userName.getText())) {
                    Toast.makeText(Registration.this, "Введите ваше имя", Toast.LENGTH_LONG).show();
                }
                if (mPassword.getText().length() < 6) {
                    Toast.makeText(Registration.this, "Введите пароль, состоящий из 6 или более символов", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(mConfirmPassword.getText())) {
                    if (mConfirmPassword == mPassword) {
                        Toast.makeText(Registration.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    }
                    else {Toast.makeText(Registration.this, "Подтвердите пароль", Toast.LENGTH_LONG).show();}
                }


                // Registration user


                Authentication.createUserWithEmailAndPassword(mEmail.getText().toString().trim(),mPassword.getText().toString().trim()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Registration.this, "Вы зарегестрировались!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registration.this, MainActivity.class));
                    } else {
                        Toast.makeText(Registration.this, "Вы не зарегестрированны, обратитесь в поддержку", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        singIn.setOnClickListener(view -> startActivity(new Intent(Registration.this, Login.class)));
    }
}
