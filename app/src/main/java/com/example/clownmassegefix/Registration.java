package com.example.clownmassegefix;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private Button singUp = (Button)findViewById(R.id.SingUp);
    private Button singIn = (Button)findViewById(R.id.SingIn);
    private EditText userName = (EditText)findViewById(R.id.UserName);
    private EditText email = (EditText)findViewById(R.id.Email);
    private EditText password = (EditText)findViewById(R.id.Password);
    private EditText confirmPassword = (EditText)findViewById(R.id.Confirm_Password);
    private FirebaseAuth Authentication;
    private FirebaseDatabase db;
    private DatabaseReference Users;


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = Authentication.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Authentication = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        Users = db.getReference("users");

        getSupportActionBar().hide();

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, Login.class));
            }
        });



        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText())) {
                    Toast.makeText(Registration.this, "Введите вашу почту", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(userName.getText())) {
                    Toast.makeText(Registration.this, "Введите ваше имя", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.getText().length() < 6) {
                    Toast.makeText(Registration.this, "Введите пароль, состоящий из 6 или более символов", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword.getText())) {
                    Toast.makeText(Registration.this, "Подтвердите пароль", Toast.LENGTH_LONG).show();
                    return;
                }


                // Registration user


                Authentication.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Registration.this, "Вы зарегестрировались!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registration.this, MainActivity.class));
                        } else {
                            Toast.makeText(Registration.this, "Вы не зарегестрированны, обратитесь в поддержку", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
