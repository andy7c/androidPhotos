package com.example.photos32.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable {

    private static final long serialVersionUID = 2L;

    public String caption;

    public SerImage image;

    public ArrayList<Tag> tags = new ArrayList<Tag>();

    public Photo() {}

    public String tagToString() {
        String tagList = "\nTags - ";
        if(tags==null) {
            return "";
        }
        for(int i = 0; i < tags.size(); i++) {
            if(i==tags.size()-1) {
                tagList+= tags.get(i).toString();
            }
            else {
                tagList+= tags.get(i).toString() + ", ";
            }
        }
        return tagList;
    }

}


