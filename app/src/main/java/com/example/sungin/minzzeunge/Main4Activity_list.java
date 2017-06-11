package com.example.sungin.minzzeunge;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main4Activity_list extends AppCompatActivity {
    ListView listView;
    Button bt_add;
   static final int REQUEST_MSG_CODE = 1;
    Person person;
    ArrayList<Person> personList = new ArrayList<>();
    PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4_list);
        init();
        person = new Person(null,"","최성인","930215","1*****","1111");

        adapter = new PersonAdapter(personList, this);
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
            intent.putExtra("MSG_PERSONDATA",person);
            startActivityForResult(intent,REQUEST_MSG_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_MSG_CODE==requestCode) {
            if(resultCode ==RESULT_OK){
                Person thisPerson;
                thisPerson = data.getParcelableExtra("remakemsg");
                personList.add(thisPerson);
                Toast.makeText(getApplicationContext(),thisPerson.filePath,Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
