package com.example.sungin.minzzeunge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by SungIn on 2017-06-11.
 */

public class PersonAdapter extends BaseAdapter{
    ArrayList<Person> data = new ArrayList<>();
    private ArrayList<Person> oriData = data ;
    Context context;
    Filter listFilter;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.list_layout,null);

        }
        return convertView;
    }
}
