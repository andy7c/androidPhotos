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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.photos32.models.Album;
import com.example.photos32.models.Photo;
import com.example.photos32.models.SerImage;

import java.io.FileDescriptor;
import java.io.Serializable;
import java.util.ArrayList;

public class OpenAlbum extends AppCompatActivity {

    ArrayList<Album> albums;
    private ListView listView;
    private String path;
    private int album_position;
    private Album curr_album;
    ActivityResultLauncher<Intent> addPhotoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_album);
        path = this.getApplicationInfo().dataDir + "/data.dat";
        albums = Albums.albums;

        System.out.println("in OpenAlbum.java now");
        Bundle bundle = getIntent().getExtras();
        curr_album = albums.get(bundle.getInt("album_position"));

        listView = findViewById(R.id.photos_list);
        PhotoList photoList = new PhotoList(this, curr_album.getPhotos());
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
                            Intent data = result.getData();
                            if (data != null) {
                                Uri uri = data.getData();
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

    public void removePhoto(View view) {
        Object o = listView.getItemAtPosition(listView.getCheckedItemPosition());
        if (o == null) {
            //error
            return;
        }
        /*
        * Add in some kind of "are you sure you want to delete?" here
        * */
        Photo photo = (Photo) o;
        PhotoList p = (PhotoList) listView.getAdapter();
        p.remove(photo);
        p.notifyDataSetChanged();
        DataHelper.save(albums, path);
    }
    public void displayPhoto(View view){
        Object o = listView.getItemAtPosition(listView.getCheckedItemPosition());
        if (o == null) {
            //error
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("album_position", album_position);
        bundle.putInt("photo_position", listView.getCheckedItemPosition());
        Intent intent = new Intent(this, Display.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }






















}