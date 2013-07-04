package com.droidshop.ui;

import com.droidshop.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ProfileImageAdapter extends BaseAdapter {
    private Context mContext;

    public ProfileImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 1;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    //create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.ic_launcher);
        return imageView;
    }
}