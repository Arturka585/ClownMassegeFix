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

import java.text.SimpleDateFormat;
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

        FirebaseAuth currentUser = FirebaseAuth.getInstance ();

        ArrayList<CustomObject> objects = new ArrayList<CustomObject>();
        CustomAdapter customAdapter = new CustomAdapter(this, objects);
        binding.listMessages.setAdapter(customAdapter);


        database.getReference("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((customAdapter != null)){ customAdapter.clear(); }
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String message = "";
                    String timekek = "";
                    try {
                        message = dataSnapshot.child("MessageText").getValue().toString();
                        timekek = dataSnapshot.child("MessageTime").getValue().toString();
                    }
                    catch(Exception e)
                    {

                    }
                    if(message.length() != 0 && timekek.length() != 0 )
                    {
                        objects.add(new CustomObject(message, timekek));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        customAdapter.notifyDataSetChanged();

        binding.sendButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty (binding.userMessageText.getText ())){
                    return;
                }else {

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    String time = format.toString();

                    database.getReference("Messages").push().setValue(new Message(
                            currentUser.getCurrentUser().getDisplayName(),
                            binding.userMessageText.getText().toString(),
                            time
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