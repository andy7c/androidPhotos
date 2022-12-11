package com.example.photos32.models;


import java.util.ArrayList;
import java.io.Serializable;

public class Album implements Serializable{

    private static final long serialVersionUID = 3L;

    private String name;

    private ArrayList<Photo> photos = new ArrayList<Photo>();

    public Album(String name) {
        this.name = name;
    }

    public Album(String name, ArrayList<Photo> photos) {
        this.name = name;
        this.photos.addAll(photos);
    }

    public void addPhoto(Photo p) {
        photos.add(p);
    }

    public void removePhoto(Photo p) {
        photos.remove(p);
    }

    public String toString() {//to string
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }
}

