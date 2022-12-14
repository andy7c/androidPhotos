package com.example.photos32;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.photos32.models.Album;
import com.example.photos32.models.Photo;
import com.example.photos32.models.Tag;

import java.util.ArrayList;

public class Display extends AppCompatActivity {
    private ArrayList<Photo> photos;
    private Album curr_album;
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
        curr_album = Albums.albums.get(album_position);
        photos = curr_album.getPhotos();
        curr = photos.get(photo_position);
        image = findViewById(R.id.picture);
        caption = findViewById(R.id.captionText);
        tags = findViewById(R.id.tagsText);
        updateDisplay();
    }

    public void editCaption(View view) {
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(curr.caption);
        b.setTitle("Edit Caption");
        b.setView(input);
        b.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                curr.caption = input.getText().toString();
                updateDisplay();
                PhotoList p = (PhotoList) OpenAlbum.listView.getAdapter();
                p.notifyDataSetChanged();
                DataHelper.save(Albums.albums, Albums.path);
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog d = b.create();
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        d.show();
    }

    public void removeTag(View view) {
        ArrayList<String> tagsArray = new ArrayList<>();
        for (Tag t : curr.tags) {
            tagsArray.add(t.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tagsArray);

        View alertView = getLayoutInflater().inflate(R.layout.remove_tag_alert, null);

        Spinner spin = alertView.findViewById(R.id.mySpinner);
        spin.setAdapter(adapter);

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setView(alertView);

        b.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tag = spin.getSelectedItem().toString();
                for (Tag t : curr.tags) {
                    if (tag.equals(t.toString())) {
                        curr.tags.remove(t);
                        updateDisplay();
                        DataHelper.save(Albums.albums, Albums.path);
                        return;
                    }
                }
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog d = b.create();
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        d.show();
    }

    public void addTag(View view) {
        String [] s = {"Person", "Location"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, s);

        View alertView = getLayoutInflater().inflate(R.layout.alert, null);

        Spinner spin = alertView.findViewById(R.id.mySpinner);
        EditText value = alertView.findViewById(R.id.tagValue);
        spin.setAdapter(adapter);

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setView(alertView);

        b.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String type = spin.getSelectedItem().toString();
                Tag t;
                if (type.isEmpty()) {
                    //error
                    return;
                } else {
                    t = new Tag(type, value.getText().toString());
                }
                curr.tags.add(t);
                updateDisplay();
                DataHelper.save(Albums.albums, Albums.path);
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog d = b.create();
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        d.show();
    }

    public void nextPhoto(View view) {
        if (photo_position == curr_album.getPhotos().size()-1) {
            //error
            return;
        }
        photo_position++;
        curr = photos.get(photo_position);
        updateDisplay();
    }

    public void prevPhoto(View view) {
        if (photo_position == 0) {
            //error
            return;
        }
        photo_position--;
        curr = photos.get(photo_position);
        updateDisplay();
    }

}
