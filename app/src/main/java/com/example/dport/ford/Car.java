package com.example.dport.ford;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by dport on 1/15/2016.
 * Derek is amazing
 */
public class Car implements Serializable{
    protected String model;
    private Integer image;
    private String description;

    public Car(){
        model = "car";
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
