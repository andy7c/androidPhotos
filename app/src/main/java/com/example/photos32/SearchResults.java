package com.example.photos32;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.photos32.models.Album;
import com.example.photos32.models.Photo;
import com.example.photos32.models.SerImage;
import com.example.photos32.models.Tag;

import java.io.FileDescriptor;
import java.io.Serializable;
import java.util.ArrayList;

public class SearchResults extends AppCompatActivity {

    public static ListView listView;
    private String path;
    private ArrayList<Photo> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        path = this.getApplicationInfo().dataDir + "/data.dat";

        results = Albums.search_results;

        listView = findViewById(R.id.photos_list);
        PhotoList photoList = new PhotoList(this, results);
        photoList.setNotifyOnChange(true);
        listView.setAdapter(photoList);
        listView.setChoiceMode(1);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                listView.setItemChecked(position, true);
            }
        });

    }

}