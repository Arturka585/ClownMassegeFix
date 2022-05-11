package com.example.clownmassegefix;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clownmassegefix.databinding.ActivityMessagesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Messages extends AppCompatActivity {


    private ActivityMessagesBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityMessagesBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());

        String currentUser = FirebaseAuth.getInstance ().getCurrentUser ().getUid ();

        List<String> messageList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.incoming_message,R.id.messageText,messageList);
        binding.listMessages.setAdapter(adapter);

        database.getReference("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                     String message = dataSnapshot.child("MessageText").getValue().toString();
                     assert message != null;
                     messageList.add(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter.notifyDataSetChanged();

        binding.sendButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty (binding.userMessageText.getText ())){
                    return;
                }else {
                    database.getReference("Messages").push().setValue(new Message(
                            "1232",
                            binding.userMessageText.getText().toString()
                    ));

                    binding.userMessageText.setText ("");
                }
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Messages.this,MainActivity.class));
            }
        });
    }
}