package com.example.sungin.minzzeunge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main5Activity_Data extends AppCompatActivity {
    ImageView imageView, imageView_gender;
    TextView tv_minF;
    TextView tv_minL;
    TextView tv_enroll;
    TextView tv_Valid;
    TextView tv_kind;
    Person thisPerson;
    final int REQUEST_MSG_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5__data);
        init();
        Intent intent = getIntent();
       thisPerson = intent.getParcelableExtra("MSG_PERSON");

        tv_minF.setText(thisPerson.minNumFirst);
        tv_minL.setText(thisPerson.minNumLast);
        tv_enroll.setText(thisPerson.enrollDate);
//        imageView_gender.set
        Bitmap bitmap = BitmapFactory.decodeFile(thisPerson.filePath);
        imageView.setImageBitmap(bitmap);
        if(thisPerson.isValid){
            tv_Valid.setText("인증완료!");
            tv_Valid.setBackgroundColor(Color.BLUE);
        }
        else {
            tv_Valid.setText("인증되지 않았습니다");
            tv_Valid.setBackgroundColor(Color.RED);
        }
        tv_Valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thisPerson.isValid){
                    Toast.makeText(getApplicationContext(),"이 신분증은 인증되었습니다",Toast.LENGTH_SHORT).show();
                }
                else{
                    certificate();
                }

            }
        });
        tv_kind.setText(thisPerson.kind);
    }
    /*
    인증하기
     */

    public void certificate(){
        Intent intent = new Intent(Main5Activity_Data.this,Main6Activity_certificate.class);
        intent.putExtra("MSG_CERTIFICATION",thisPerson);
        startActivityForResult(intent,REQUEST_MSG_CODE);
    }

    public void init() {
        imageView = (ImageView) findViewById(R.id.imgno);
        tv_minF = (TextView)findViewById(R.id.tvMinFirst);
        tv_minL = (TextView)findViewById(R.id.tvMinLast);
        tv_enroll = (TextView)findViewById(R.id.tvRegdate);
        tv_Valid = (TextView)findViewById(R.id.tv_isValid);
        imageView_gender = (ImageView)findViewById(R.id.imageView_gender);
        tv_kind = (TextView)findViewById(R.id.tv_kind);
    }
    public void onClick(View v){
        if( v.getId() == R.id.btnback){
            Intent intent = new Intent(Main5Activity_Data.this,Main4Activity_list.class);
            intent.putExtra("remakemsg",thisPerson);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MSG_CODE) {
            if (resultCode == RESULT_OK) {
                Person person = data.getParcelableExtra("remakemsg");
                thisPerson = person;
                tv_Valid.setText("인증완료!");
                tv_Valid.setBackgroundColor(Color.BLUE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
