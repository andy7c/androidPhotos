package com.example.photos32.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;


public class SerImage implements Serializable {
    private byte[] bytes;

    public SerImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bytes = stream.toByteArray();
    }

    public Bitmap getBitmap() {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
