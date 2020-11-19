package com.iot.its_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class CustomAdapter extends ArrayAdapter<VehiclePenalty> {
    Context context;
    int resourse;
    ArrayList<VehiclePenalty> items;
    ImageView imageView;
    TextView dayTv;
    TextView timeTv;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VehiclePenalty> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourse = resource;
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView =  inflater.inflate(R.layout.custom_listview, null);
            imageView = convertView.findViewById(R.id.cus_img);
            dayTv = convertView.findViewById(R.id.day_tv);
            timeTv = convertView.findViewById(R.id.time_tv);
        }
        VehiclePenalty currentItems = items.get(position);
        String[] s = currentItems.time.split(" ");
        String day = s[0];
        String[] s2 = s[1].split(":");
        String time = s2[0] + ":"+s2[1];
        timeTv.setText(time);
        dayTv.setText(day);
        Utils.getImage(currentItems.speed + "", imageView);
        return convertView;
    }

    public void setItems( ArrayList<VehiclePenalty> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }



}
