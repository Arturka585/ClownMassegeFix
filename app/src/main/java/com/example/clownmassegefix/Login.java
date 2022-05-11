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

import com.example.clownmassegefix.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth Authentication= FirebaseAuth.getInstance();
        binding = ActivityLoginBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());


        binding.SingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.Email.getText())) {
                    Toast.makeText(Login.this, "Введите вашу почту", Toast.LENGTH_LONG).show();
                }
                if (binding.Password.getText().length() < 6) {
                    Toast.makeText(Login.this, "Введите пароль, состоящий из 6 или более символов", Toast.LENGTH_LONG).show();
                }else {
                Authentication.signInWithEmailAndPassword (binding.Email.getText ().toString ().trim (),binding.Password.getText ().toString ().trim ()).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful ()){
                         Toast.makeText (Login.this, "Вы успешно авторизировались!", Toast.LENGTH_SHORT).show ();
                         startActivity (new Intent (Login.this,Messages.class));
                         Login.this.finish ();
                     }else {
                         Toast.makeText (Login.this, "Вы не авторизировались, обратитесь в поддержку!!", Toast.LENGTH_SHORT).show ();
                     }
                    }
                });
                }
            }
        });

        binding.Register.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity (new Intent (Login.this, Registration.class));
                Login.this.finish ();
            }
        });
    }

}