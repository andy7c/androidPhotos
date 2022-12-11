package com.example.photos32;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddEditAlbum extends AppCompatActivity {

    private EditText album_name;

    public void save(View view) {
        // gather all data from text fields
        String name = album_name.getText().toString();
        // make Bundle
        Bundle bundle = new Bundle();
        bundle.putString("album_name", name);
        // send info back to caller (activity that launched this activity)
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish(); // pops activity from the call stack, returns to parent
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_album);

        album_name = findViewById(R.id.album_name);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            album_name.setText(bundle.getString("album_name"));
        }

    }
}