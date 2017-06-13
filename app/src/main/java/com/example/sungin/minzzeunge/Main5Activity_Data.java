package com.example.sungin.minzzeunge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Main5Activity_Data extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5__data);
        init();
        Intent intent = getIntent();
        Person thisPerson = intent.getParcelableExtra("MSG_PERSON");

        Bitmap bitmap = BitmapFactory.decodeFile(thisPerson.filePath);
        imageView.setImageBitmap(bitmap);
    }
    public void init(){
        imageView = (ImageView)findViewById(R.id.imgno);
    }
}
