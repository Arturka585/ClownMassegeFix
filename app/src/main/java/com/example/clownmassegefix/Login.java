package com.example.clownmassegefix;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.clownmassegefix.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

         @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth Authentication= FirebaseAuth.getInstance();
        binding = ActivityLoginBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());

             //проверка на пустоту ввода номера телефона и запуск отправки кода подтверждения

             binding.SingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.Phone.getText())) {
                    Toast.makeText(Login.this, "Введите ваш номер телефона", Toast.LENGTH_LONG).show();
                }
                else {
                    startPhoneNumberVerification(binding.Phone.getText().toString().trim());

                }

            }
        });

    }

    // вход по номеру телефона

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        } else {
                        }
                    }
                });

    }
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Login.this, "Не верный номер телефона", Toast.LENGTH_LONG).show();
                Log.e(TAG,e.getMessage());
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                Toast.makeText(Login.this, "Код подтверждения был отправлен на ваш номер телефона" , Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet,
                        findViewById(R.id.bottomSheetsContainer));

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Login.this, R.style.BottomSheetsDialogTheme);
                bottomSheetDialog.findViewById(R.id.confirmButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText verifyCode = bottomSheetDialog.findViewById(R.id.verificationCode);
                        verifyPhoneNumberWithCode(mVerificationId,verifyCode.getText().toString());
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
    }

}