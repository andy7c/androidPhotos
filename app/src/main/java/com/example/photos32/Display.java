package com.example.photos32;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

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
        TextView caption = findViewById(R.id.caption);
        caption.setText("asdf");


    }
}
