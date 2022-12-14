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
    private ListView listView;
    public static ArrayList<Album> albums;
    public static String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_list);
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
        listView = findViewById(R.id.albums_list);
        listView.setAdapter(adapter);
        listView.setChoiceMode(1);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                listView.setItemChecked(position, true);
            }
        });

    }
    public void openAlbum(View view) {
        //make intent and bundle and send to new screen
        Object o = listView.getItemAtPosition(listView.getCheckedItemPosition());
        if (o == null) {
            //error
            return;
        }
        Album a = (Album) o;
        Bundle bundle = new Bundle();
        bundle.putInt("album_position", listView.getCheckedItemPosition());
        Intent intent = new Intent(this, OpenAlbum.class);
        intent.putExtras(bundle);
        System.out.println("starting activity");
        startActivity(intent);
    }

    public void removeAlbum(View view) {
        Object o = listView.getItemAtPosition(listView.getCheckedItemPosition());
        if (o == null) {
            //error
            return;
        }
        Album a = (Album) o;
        final ArrayAdapter<Album> adapter = (ArrayAdapter<Album>) listView.getAdapter();
        adapter.remove(a);
        ArrayList<Album> al = new ArrayList<>();
        for (int i = 0; i < adapter.getCount(); i++) {
            al.add(adapter.getItem(i));
        }
        DataHelper.save(al, path);
    }

    public void addAlbum(View view) {
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        final ArrayAdapter<Album> adapter = (ArrayAdapter<Album>) listView.getAdapter();
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        b.setTitle("Enter a name for the album");
        b.setView(input);
        b.setPositiveButton("Add", new DialogInterface.OnClickListener() {
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
    public void editAlbum(View view) {
        Object o = listView.getItemAtPosition(listView.getCheckedItemPosition());
        if (o == null) {
            //error
            System.out.println("nothing selected");
            return;
        }
        Album a = (Album) o;
        final AlertDialog.Builder b = new AlertDialog.Builder(this);
        final ArrayAdapter<Album> adapter = (ArrayAdapter<Album>) listView.getAdapter();
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(a.getName());
        b.setTitle("Enter a new name for the album");
        b.setView(input);
        b.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                a.setName(input.getText().toString());
                ArrayList<Album> al = new ArrayList<>();
                for (int i = 0; i < adapter.getCount(); i++) {
                    al.add(adapter.getItem(i));
                }
                DataHelper.save(al, path);
                adapter.notifyDataSetChanged();
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

}