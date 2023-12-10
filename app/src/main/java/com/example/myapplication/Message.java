package com.example.myapplication;

public class Message {
    private String sender;
    private String text;

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
