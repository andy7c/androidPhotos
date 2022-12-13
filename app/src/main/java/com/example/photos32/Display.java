package com.example.photos32;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photos32.models.Photo;

import java.util.ArrayList;

public class Display extends AppCompatActivity {
    private ArrayList<Photo> photos;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        Bundle bundle = getIntent().getExtras();
        photos =  (ArrayList<Photo>) bundle.getSerializable("photos");
        position = bundle.getInt("position");
        Photo p = photos.get(position);
        ImageView image = findViewById(R.id.picture);
        image.setImageBitmap(p.image.getBitmap());
        TextView caption = findViewById(R.id.captionText);
        caption.setText("asdf");


    }
}
