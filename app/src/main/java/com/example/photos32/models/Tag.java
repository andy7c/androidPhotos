package com.example.photos32.models;

import java.io.Serializable;

public class Tag implements Serializable {

    private static final long serialVersionUID = 6L;

    public String name;

    public String value;

    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return name + " : " + value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
