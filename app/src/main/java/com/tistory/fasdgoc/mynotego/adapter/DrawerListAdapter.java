package com.tistory.fasdgoc.mynotego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tistory.fasdgoc.mynotego.R;

import java.util.ArrayList;

/**
 * Created by fasdg on 2016-10-23.
 */

public class DrawerListAdapter extends BaseAdapter {
    public static class DrawerItem {
        private int img;
        private String txt;

        public DrawerItem(int img, String txt) {
            this.img = img;
            this.txt = txt;
        }
    }

    private Context context;
    private ArrayList<DrawerItem> array;
    private int layout;

    private LayoutInflater inflater;

    public DrawerListAdapter(Context context, ArrayList<DrawerItem> array, int layout) {
        this.context = context;
        this.array = array;
        this.layout = layout;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        ((ImageView)convertView.findViewById(R.id.img))
                .setImageResource(array.get(position).img);
        ((TextView)convertView.findViewById(R.id.txt))
                .setText(array.get(position).txt);

        return convertView;
    }
}
