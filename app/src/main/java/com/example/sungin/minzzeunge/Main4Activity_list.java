package com.example.sungin.minzzeunge;

import android.content.DialogInterface;
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
    static final int REQUEST_MSG_CODE3 = 3;
    PersonAdapter adapter;
    ArrayList<Person> personList = new ArrayList<>();
    int size = 0;
    int modifyPosition = 0;
    MySQLiteDatabase mySQLiteDatabase;
    boolean noitem = true;
    boolean firstDB = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4_list);
        init();

        mySQLiteDatabase = new MySQLiteDatabase(this,"persondb",null,1);//DB 가져오기

        Intent gintent = getIntent();
        Person p = gintent.getParcelableExtra("MSG_LOGIN");
        Person person;
        if(p!=null){
             person = new Person("", p.name, p.minNumFirst, p.minNumLast, "", null, false);
        }
        else {
            personList = mySQLiteDatabase.getAllPersonData();
             person = personList.get(0);
            noitem = false;
        }
        adapter = new PersonAdapter(personList, this);
        listView.setAdapter(adapter);

        if(firstDB){
            adapter.notifyDataSetChanged();
            firstDB = false;
        }
        if (noitem ) {
            person.kind = "신분증이 등록되지 않았습니다\n신분증을 추가하세요";
            personList.add(person);
            adapter.notifyDataSetChanged();
        }
        final Intent intent = new Intent(this, Main5Activity_Data.class);

        /*
        ListView OnClickListener 세팅
         */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (noitem)
                    Toast.makeText(getApplicationContext(), "등록된 신분증이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                else{
                    intent.putExtra("MSG_PERSON", personList.get(position));
                    startActivityForResult(intent, REQUEST_MSG_CODE2);
                    modifyPosition = position;
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(Main4Activity_list.this);
                String[] item = {"신분증 수정", "신분증 삭제"};
                dlg.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) { // 신분증 수정
                            if (personList.get(position).isValid)
                                Toast.makeText(getApplicationContext(), "<인증완료> 된 신분증은 수정할 수 없습니다", Toast.LENGTH_SHORT).show();
                            else if (noitem)
                                Toast.makeText(getApplicationContext(), "등록된 신분증이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            else {
                                Intent intent1 = new Intent(Main4Activity_list.this, Main3Activity_enroll.class);
                                intent1.putExtra("MSG_MODIFY", personList.get(position));
                                modifyPosition = position;
                                startActivityForResult(intent1, REQUEST_MSG_CODE3);
                            }
                        }
                        if (which == 1) { //신분증 삭제
                            if (noitem)
                                Toast.makeText(getApplicationContext(), "등록된 신분증이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            else { // Dialog 를 통해 삭제여부를 다시 묻고, 삭제
                                AlertDialog.Builder dlg = new AlertDialog.Builder(Main4Activity_list.this);
                                dlg.setTitle("신분증 삭제")
                                        .setIcon(R.mipmap.ic_launcher)
                                        .setMessage("정말로 신분증을 삭제하시겠습니까?");
                                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        personList.remove(position);
                                        mySQLiteDatabase.removeDB(personList.get(position).filePath);

                                        if( personList.size() == 0 ) noitem = true;
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(), "선택한 신분증이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNegativeButton("취소",null);
                                dlg.show();
                            }

                        }
                    }
                }).show();

                return true;
            }
        });

        /*
        DB에서 가져와서 personList에 넘겨 리스트뷰 생성.
         */



    }

    public void init() {
        listView = (ListView) findViewById(R.id.ListView);
        bt_add = (Button) findViewById(R.id.button_Add);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button_Add) {
            Intent intent = new Intent(this, Main3Activity_enroll.class);
            intent.putExtra("MSG_PERSONDATA", new Person("", "최성인", "930215", "1234567", "", null, false));
            startActivityForResult(intent, REQUEST_MSG_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MSG_CODE) {
            if (resultCode == RESULT_OK) {
                Person thisPerson = data.getParcelableExtra("remakemsg");

                if (noitem) {
                    personList.remove(0);
                }
                personList.add(thisPerson);
                mySQLiteDatabase.addToDB(thisPerson);
                noitem=false;
                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == REQUEST_MSG_CODE2) { //인증여부 수정 받아오기
            if (resultCode == RESULT_OK) {
                Person thisPerson = data.getParcelableExtra("remakemsg");
                Toast.makeText(getApplicationContext(),"선택한 신분증이 인증완료 되었습니다",Toast.LENGTH_SHORT).show();
                personList.get(modifyPosition).isValid = thisPerson.isValid;

                mySQLiteDatabase.removeDB(personList.get(modifyPosition).filePath); //DB에는 수정작업을 넣지 않아, 삭제하고 다시 만듦
                mySQLiteDatabase.addToDB(thisPerson);

                modifyPosition = 0;
                adapter.notifyDataSetChanged();

            }
        }
        if (requestCode == REQUEST_MSG_CODE3) { //데이터 수정 받아오기
            if (resultCode == RESULT_OK) {
                Person thisPerson = data.getParcelableExtra("remakemsg");

                //삭제, 수정시 DB도 같이 수정
                mySQLiteDatabase.removeDB(personList.get(modifyPosition).filePath); //DB에는 수정작업을 넣지 않아, 삭제하고 다시 만듦
                personList.remove(modifyPosition);

                personList.add(modifyPosition, thisPerson);
                mySQLiteDatabase.addToDB(thisPerson);

                Toast.makeText(getApplicationContext(),"수정되었습니다",Toast.LENGTH_SHORT).show();

                modifyPosition = 0; // 초기화
                adapter.notifyDataSetChanged();
            }
        }
    }

    /*
    메뉴추가 --- 로그아웃, 개인정보 수정
     */

}
