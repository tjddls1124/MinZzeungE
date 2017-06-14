package com.example.sungin.minzzeunge;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class Main2Activity_login extends AppCompatActivity {
    EditText et1,et2,et3;
    MySQLiteDatabase mySQLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_login);
        init();

        File folder = new File("/data/data/com.example.sungin.minzzeunge/databases/");

        if (!folder.exists()) {
            folder.mkdir();
        }
        final File file = new File("/data/data/com.example.sungin.minzzeunge/databases/persondb");

        Handler handler = new Handler() ;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (file.exists()) {

                    Toast.makeText(getApplicationContext(),"저장된 신분증이 있습니다.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Main2Activity_login.this,
                            Main4Activity_list.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"저장된 신분증이 없습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        },500);


    }
    public void init(){
        et1 = (EditText)findViewById(R.id.editText);
        et2 = (EditText)findViewById(R.id.editText2);
        et3 = (EditText)findViewById(R.id.editText3);

    }
    public void onClick(View v) {
        Person p = new Person();

        Intent intent = new Intent(Main2Activity_login.this, Main4Activity_list.class);
        p.name = et1.getText().toString();
        p.minNumFirst = et2.getText().toString();
        p.minNumLast = et3.getText().toString();
        intent.putExtra("MSG_LOGIN",p);
        startActivity(intent);
        finish();

    }

}
