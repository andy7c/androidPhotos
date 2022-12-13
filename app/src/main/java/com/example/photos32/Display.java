package com.example.photos32;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photos32.models.Photo;

import java.util.ArrayList;

public class Display extends AppCompatActivity {
    private ArrayList<Photo> photos;
    private Photo curr;
    private int photo_position;
    private int album_position;
    private ImageView image;
    private TextView caption;
    private TextView tags;

    private void updateDisplay() {
        image.setImageBitmap(curr.image.getBitmap());
        caption.setText(curr.caption);
        tags.setText(curr.tagToString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        Bundle bundle = getIntent().getExtras();
        photo_position = bundle.getInt("photo_position");
        album_position = bundle.getInt("album_position");
        photos = Albums.albums.get(album_position).getPhotos();
        curr = photos.get(photo_position);
        image = findViewById(R.id.picture);
        caption = findViewById(R.id.captionText);
        tags = findViewById(R.id.tagsText);
        updateDisplay();
    }
}
