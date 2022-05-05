package com.example.clownmassegefix;

import java.util.Date;

public class Message {
    public String Username;
    public String MessageText;
    private long MessageTime;

    public Message(){}
    public Message(String Username,String MessageText){
        this.Username = Username;
        this.MessageText = MessageText;

        this.MessageTime = new Date ().getTime ();
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public long getMessageTime() {
        return MessageTime;
    }

    public void setMessageTime(long messageTime) {
        MessageTime = messageTime;
    }
}
