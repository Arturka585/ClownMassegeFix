package com.example.clownmassegefix;

import java.util.Date;

public class Message {
    public String Username;
    public String MessageText;
    private String MessageTime;

    public Message(){}

    public Message(String Username,String MessageText,String MessageTime){
        this.Username =  Username;
        this.MessageText = MessageText;

    }
}
