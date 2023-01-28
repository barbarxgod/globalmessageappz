package com.example.myapplication;

public class DirectoryModel {
    private String name, number, image;

    public DirectoryModel(String name, String number, String image){
        this.name=name;
        this.number=number;
        this.image=image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNumber(){
        return number;
    }
    public String getImage(){
        return image;
    }
}
