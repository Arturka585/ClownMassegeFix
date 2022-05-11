package com.example.clownmassegefix;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Message {
    public String Username;
    public String MessageText;
    private long MessageTime;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");

    public Message(){}

    public Message(String Username,String MessageText){
        this.Username =  Username;
        this.MessageText = MessageText;

        this.MessageTime = new Date ().getTime ();
    }

    public long getMessageTime() {
        return MessageTime;
    }
}
