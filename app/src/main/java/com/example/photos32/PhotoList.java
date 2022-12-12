package com.example.photos32;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photos32.models.Photo;

import java.util.ArrayList;

public class PhotoList extends ArrayAdapter {
    private ArrayList<Photo> photos;
    private Activity context;
    private boolean isChecked;

    public PhotoList(Activity context, ArrayList<Photo> p) {
        super(context, R.layout.row_item, p);
        photos = p;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Photo p = photos.get(position);
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item, null, true);
        TextView textViewCaption = (TextView) row.findViewById(R.id.textViewCaption);
        ImageView image = (ImageView) row.findViewById(R.id.imageView);
        textViewCaption.setText(p.caption);
        image.setImageBitmap(p.image.getBitmap());
        return row;
    }
}
