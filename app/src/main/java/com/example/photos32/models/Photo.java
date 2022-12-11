package com.example.photos32.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable {

    private static final long serialVersionUID = 2L;

    public String caption;

    public SerImage image;

    public ArrayList<Tag> tags = new ArrayList<Tag>();

    public Photo() {}

}


