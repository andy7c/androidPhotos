package com.example.photos32;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.photos32.models.Album;

import java.util.ArrayList;

public class OpenAlbum extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Album> albums;
    private int position;
    private Album curr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_album);

        Bundle bundle = getIntent().getExtras();
        albums = (ArrayList<Album>) bundle.getSerializable("albums");
        position = bundle.getInt("position");
        curr = albums.get(position);

        listView = findViewById(R.id.photos_list);
        PhotoList photoList = new PhotoList(this, curr.getPhotos());
        listView.setAdapter(photoList);
    }
}