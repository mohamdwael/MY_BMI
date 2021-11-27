package com.example.my_bmi.model;

public class OldStatues {
    String dateOfB;
    String weight;
    String Length;

    public OldStatues(String dateOfB, String weight, String length) {
        this.dateOfB = dateOfB;
        this.weight = weight;
        Length = length;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String length) {
        Length = length;
    }
}
