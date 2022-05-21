package com.example.clownmassegefix;

import static java.util.Calendar.getInstance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clownmassegefix.databinding.ActivityMessagesBinding;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.text.format.DateFormat;

public class Messages extends AppCompatActivity {


    private ActivityMessagesBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseListAdapter<Message> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityMessagesBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());

        String currentUser = FirebaseAuth.getInstance ().getCurrentUser ().getUid ();

        List<String> messageList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.incoming_message,R.id.messageText,messageList);
        binding.listMessages.setAdapter(adapter);

        List<String> timeList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.incoming_message,R.id.timeText,timeList);
        binding.listMessages.setAdapter(arrayAdapter);

        database.getReference("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((adapter != null)){ adapter.clear();}
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                     String message = dataSnapshot.child("MessageText").getValue().toString();
                     assert message != null;
                     messageList.add(message);;
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
                    SimpleDateFormat format = new SimpleDateFormat ("HH:mm");
                    String time = format.format (new Date());

                    HashMap<String,Object> MessageText = new HashMap<> ();


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