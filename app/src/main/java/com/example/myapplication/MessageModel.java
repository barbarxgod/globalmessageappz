package com.example.myapplication;

import java.util.List;

public class MessageModel {
    private String name, description, uid;

    public MessageModel(String name, String description, String uid) {
        this.name=name;
        this.description=description;
        this.uid=uid;
    }
    public String getName() { return name; }

    public String getDescripton() { return description; }

    public void setName(String name) { this.name = name; }
}