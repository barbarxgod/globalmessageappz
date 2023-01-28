package com.example.myapplication;

import java.util.Collections;
import java.util.List;

public class GroupModel {
    private String name, description, image, uid;
    private List<String> numbers;

    public GroupModel(String name, String description, String image, List<String> uid, String numbers) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.uid = String.valueOf(uid);
        this.numbers = Collections.singletonList(numbers);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {return description;}

    public String getImage() {return image;}

    public String getUid() {return uid;}

    public List<String> getNumbers() {return numbers;}
}
