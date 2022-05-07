package com.example.clownmassegefix;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clownmassegefix.databinding.ActivityMessagesBinding;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Messages extends AppCompatActivity {


    private ActivityMessagesBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityMessagesBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());
        String currentUser = FirebaseAuth.getInstance ().getCurrentUser ().getUid ();
        FirebaseUser auth = FirebaseAuth.getInstance ().getCurrentUser ();

        FirebaseListOptions options;
        options = new FirebaseListOptions.Builder<Message> ()
                .setQuery (databaseReference.child ("users").child ("Messages"),Message.class)
                .setLayout (R.layout.incoming_message)
                .build ();

        FirebaseListAdapter<com.example.clownmassegefix.Message> adapter = new FirebaseListAdapter<com.example.clownmassegefix.Message> (options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull com.example.clownmassegefix.Message model, int position) {
                TextView userName = v.findViewById (R.id.userNameText);
                TextView messageText = v.findViewById (R.id.messageText);
                TextView time = v.findViewById (R.id.timeText);

                databaseReference.child("Messages").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                                userName.setText (snapshot.child ("name").getValue ().toString ());
                                messageText.setText(snapshot.child("Messages").getValue().toString());
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            messageText.setText (model.getMessageText ());
            time.setText (DateFormat.format("HH:mm",model.getMessageTime ()));
    }
};

        binding.listMessages.setAdapter (adapter);

        binding.sendButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty (binding.userMessageText.getText ())){
                    return;
                }else {

                    SimpleDateFormat format = new SimpleDateFormat ("HH:mm");
                    String time = format.toString();

                   HashMap<String,Object> message = new HashMap<> ();
                   message.put ("message",binding.userMessageText.getText ().toString ());

                    database.getReference ().child ("Messages").push ().setValue (message,time);
                    binding.userMessageText.setText ("");
                }
            }
        });
    }
}