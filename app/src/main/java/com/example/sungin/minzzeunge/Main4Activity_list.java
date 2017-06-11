package com.example.sungin.minzzeunge;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Main4Activity_list extends AppCompatActivity {
    ListView listView;
    Button bt_add;
    int REQUEST_MSG_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4_list);
        init();

        ArrayList<Person> personList = new ArrayList<>();
        PersonAdapter adapter = new PersonAdapter(personList, this);
        listView.setAdapter(adapter);


    }
    public void setListView() {

    }

    public void init() {
        listView = (ListView) findViewById(R.id.ListView);
        bt_add = (Button)findViewById(R.id.button_Add);
    }
    public void onClick(View v){
        if( v.getId()==R.id.button_Add) {
            Intent intent = new Intent(this, Main3Activity_enroll.class);
            startActivity(intent);
        }
    }
}
