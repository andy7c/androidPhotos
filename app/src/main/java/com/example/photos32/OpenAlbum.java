package com.example.photos32;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ListView;

import com.example.photos32.models.Album;
import com.example.photos32.models.Photo;
import com.example.photos32.models.SerImage;

import java.io.FileDescriptor;
import java.util.ArrayList;

public class OpenAlbum extends AppCompatActivity {

    private ListView listView;
    private String path;
    private ArrayList<Album> albums;
    private int position;
    private Album curr;
    ActivityResultLauncher<Intent> addPhotoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_album);
        path = this.getApplicationInfo().dataDir + "/data.dat";

        Bundle bundle = getIntent().getExtras();
        albums = (ArrayList<Album>) bundle.getSerializable("albums");
        position = bundle.getInt("position");
        curr = albums.get(position);

        listView = findViewById(R.id.photos_list);
        PhotoList photoList = new PhotoList(this, curr.getPhotos());
        photoList.setNotifyOnChange(true);
        listView.setAdapter(photoList);

        registerActivities();
    }

    private void registerActivities() {
        addPhotoActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent resultData = result.getData();
                            if (resultData != null) {
                                Uri uri = resultData.getData();
                                Bitmap bitmap = null;
                                try {
                                    ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "r");
                                    FileDescriptor fd = pfd.getFileDescriptor();
                                    bitmap = BitmapFactory.decodeFileDescriptor(fd);
                                    pfd.close();
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                                String caption = uri.getLastPathSegment();
                                SerImage s = new SerImage(bitmap);
                                Photo photo = new Photo();
                                photo.caption = caption;
                                photo.image = s;
                                PhotoList adapter = (PhotoList) listView.getAdapter();
                                adapter.add(photo);
                                DataHelper.save(albums, path);
                            }


                        }
                    }
                });
    }

    public void addPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        addPhotoActivity.launch(intent);
    }






















}