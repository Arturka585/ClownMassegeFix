package com.example.clownmassegefix;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clownmassegefix.databinding.ActivityLoginWithEmail2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginWithEmail extends AppCompatActivity {


        private ActivityLoginWithEmail2Binding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate (savedInstanceState);
            binding = ActivityLoginWithEmail2Binding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            FirebaseAuth authentication = FirebaseAuth.getInstance();

                    binding.SingIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(binding.Email.getText())) {
                                Toast.makeText(LoginWithEmail.this, "Введите вашу почту", Toast.LENGTH_LONG).show();
                            }
                            if (TextUtils.isEmpty(binding.Password.getText())) {
                                Toast.makeText(LoginWithEmail.this, "Введите пароль", Toast.LENGTH_LONG).show();
                            }
                            authentication.signInWithEmailAndPassword (binding.Email.getText ().toString ().trim (),binding.Password.getText ().toString ().trim ()).addOnCompleteListener (new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful ()){
                                        Toast.makeText (LoginWithEmail.this, "Вы успешно авторизировались!", Toast.LENGTH_SHORT).show ();
                                        startActivity (new Intent (LoginWithEmail.this,MainActivity.class));
                                        LoginWithEmail.this.finish ();
                                    }else {
                                        Toast.makeText (LoginWithEmail.this, "Вы не авторизировались, обратитесь в поддержку!!", Toast.LENGTH_SHORT).show ();
                                    }
                                }
                            });
                        }
                    });

                    binding.SingUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent (LoginWithEmail.this,Registration.class));
                        }
                    });

    }
}