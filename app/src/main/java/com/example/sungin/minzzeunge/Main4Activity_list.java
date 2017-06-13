package com.example.sungin.minzzeunge;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main4Activity_list extends AppCompatActivity {
    ListView listView;
    Button bt_add;
    static final int REQUEST_MSG_CODE = 1;
    static final int REQUEST_MSG_CODE2 = 2;
    PersonAdapter adapter;
    ArrayList<Person> personList = new ArrayList<>();
    int size = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4_list);
        init();
        Person person = new Person(null, "", "최성인", "930215", "1234567", "1111");
        adapter = new PersonAdapter(personList, this);
        listView.setAdapter(adapter);
        if (size == 0) {
            person.kind = "신분증을 추가하세요";
            personList.add(person);
            adapter.notifyDataSetChanged();
        }
        final Intent intent = new Intent(this,Main5Activity_Data.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("MSG_PERSON", personList.get(position));
                startActivityForResult(intent, REQUEST_MSG_CODE2);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main4Activity_list.this, Main5Activity_Data.class);
                intent.putExtra("MSG_PERSON", personList.get(position));
                startActivityForResult(intent, REQUEST_MSG_CODE2);
            }
        });

    }

    public void init() {
        listView = (ListView) findViewById(R.id.ListView);
        bt_add = (Button) findViewById(R.id.button_Add);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button_Add) {
            Intent intent = new Intent(this, Main3Activity_enroll.class);
            intent.putExtra("MSG_PERSONDATA", new Person(null, "", "최성인", "930215", "1234567", "1111"));
            startActivityForResult(intent, REQUEST_MSG_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MSG_CODE) {
            if (resultCode == RESULT_OK) {
                Person thisPerson = data.getParcelableExtra("remakemsg");

                if (size == 0) {
                    personList.remove(0);
                }
                personList.add(thisPerson);
                Toast.makeText(getApplicationContext(), personList.get(size).kind + "" + personList.get(size).filePath, Toast.LENGTH_SHORT).show();
                size++;
                adapter.notifyDataSetChanged();
            }
        }
        if(requestCode == REQUEST_MSG_CODE2){
            if(resultCode ==RESULT_OK){

            }
        }
    }

    /*
    메뉴추가 --- 로그아웃, 개인정보 수정
     */

}
