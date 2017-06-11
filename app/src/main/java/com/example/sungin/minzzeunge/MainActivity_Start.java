package com.example.sungin.minzzeunge;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity_Start extends AppCompatActivity {
    Thread thread=new Thread(){
        @Override
        public void run() {
            super.run();
        }
    };
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thread.start();

    }
}
