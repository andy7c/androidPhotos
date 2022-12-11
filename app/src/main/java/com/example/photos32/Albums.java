package com.example.photos32;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.photos32.models.Album;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Albums extends AppCompatActivity {

    private ArrayList<Album> albums;
    private ListView listView;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_list);

        listView = findViewById(R.id.albums_list);

        path = this.getApplicationInfo().dataDir + "/data.dat";

        File data = new File(path);

        if (!data.isFile() || !data.exists()) {
            try {
                data.createNewFile();
                albums = new ArrayList<Album>();
                DataHelper.save(albums, path);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        // File exists, proceed to read it
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            albums = (ArrayList<Album>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //populate the list view
        ArrayAdapter<Album> adapter = new ArrayAdapter<>(this, R.layout.album, albums);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                listView.setItemChecked(position, true);
            }
        });

    }

    public void addAlbum(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ArrayAdapter<Album> adapter = (ArrayAdapter<Album>) listView.getAdapter();
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setTitle("Enter a name for the album");
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Album newAlbum = new Album(input.getText().toString());

                adapter.add(newAlbum);
                ArrayList<Album> al = new ArrayList<>();
                for (int i = 0; i < adapter.getCount(); i++) {
                    al.add(adapter.getItem(i));
                }
                DataHelper.save(al, path);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

}