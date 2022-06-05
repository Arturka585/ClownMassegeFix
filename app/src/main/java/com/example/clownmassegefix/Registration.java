package com.example.clownmassegefix;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clownmassegefix.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Registration extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

        FirebaseAuth Authentication = FirebaseAuth.getInstance();
        DatabaseReference rootReference = FirebaseDatabase.getInstance ().getReference ("Users");


        binding.Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.Phone.getText())) {
                    Toast.makeText(Registration.this, "Введите вашу почту", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(binding.UserName.getText())) {
                    Toast.makeText(Registration.this, "Введите ваше имя", Toast.LENGTH_LONG).show();
                }
                if (binding.Password.getText().length() < 6) {
                    Toast.makeText(Registration.this, "Введите пароль, состоящий из 6 или более символов", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(binding.ConfirmPassword.getText())) {
                    if (binding.ConfirmPassword == binding.Password) {
                        Toast.makeText(Registration.this, "Пароли не совпадают", Toast.LENGTH_LONG).show();
                    }
                    else {Toast.makeText(Registration.this, "Подтвердите пароль", Toast.LENGTH_LONG).show();}
                }


                // Registration user

                Authentication.createUserWithEmailAndPassword(binding.Phone.getText().toString().trim(),binding.Password.getText().toString().trim()).addOnCompleteListener(task ->
                {
                    if (task.isSuccessful()) {
                        Toast.makeText(Registration.this, "Вы зарегестрировались! 😊", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registration.this, MainActivity.class));
                        Registration.this.finish ();

                        HashMap<String, Object> profile = new HashMap<> ();
                        profile.put ("name",binding.UserName.getText ().toString ());

                        rootReference.child (Authentication.getCurrentUser ().getUid ()).setValue (profile);

                        FirebaseUser user = Authentication.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(binding.UserName.getText().toString()).build();
                        user.updateProfile(profileUpdates);
                    } else {

                        Toast.makeText(Registration.this, "Вы не зарегестрированны, обратитесь в поддержку 😅", Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });


        binding.SingIn.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity (new Intent (Registration.this, Login.class));
                Registration.this.finish ();
            }
        });
    }
}
