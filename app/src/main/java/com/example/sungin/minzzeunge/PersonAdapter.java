package com.example.sungin.minzzeunge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SungIn on 2017-06-11.
 */

public class PersonAdapter extends BaseAdapter {
    ArrayList<Person> data = new ArrayList<>();
    private ArrayList<Person> oriData = data;
    Context context;
    Filter listFilter;

    public PersonAdapter(ArrayList<Person> data, Context context) {
        this.data = data;
        this.oriData = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.list_layout, null);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
            TextView textView_name =(TextView)convertView.findViewById(R.id.textView_Name);
            TextView textView_MinNum = (TextView)convertView.findViewById(R.id.textView_MinNum);
            TextView textView_propreties = (TextView)convertView.findViewById(R.id.textView_Properties);
            Person thePerson = data.get(position);

            textView_name.setText("이름 : "+thePerson.name+"**"+thePerson.filePath + "**"+thePerson.kind);
            textView_MinNum.setText("주민등록 번호 :" +thePerson.minNumFirst + "-"+ thePerson.minNumLast);
            textView_propreties.setText("("+thePerson.age+ "세, "+thePerson.gender+")");
            if(thePerson.gender.equals("남")) textView_propreties.setTextColor(Color.BLUE);
            else textView_propreties.setTextColor(Color.YELLOW);

            if(thePerson.filePath !=null){
                Bitmap bitmap = BitmapFactory.decodeFile(thePerson.filePath);
                imageView.setImageBitmap(bitmap);
            }
        }
        return convertView;
    }
}
