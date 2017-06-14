package com.example.sungin.minzzeunge;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tjddl on 2017-06-15.
 */


public class MySQLiteDatabase extends SQLiteOpenHelper {
    private Context context;

    public MySQLiteDatabase(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int
                                    version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table if not exists person (" +
                "name text not null," +
                "kind text not null," +
                "gender text not null," +
                "minNumFirst text not null," +
                "minNumLast text not null," +
                "enroll text," +
                "filePath text primary key," +
                "age integer not null," +
                "birthYear integer not null," +
                "isValid integer not null" +
                ")";

        db.execSQL(sql);
        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        String sql = "drop table if exists person";
        onCreate(db);
    }

    public void addToDB(Person person) {
        SQLiteDatabase db = getWritableDatabase();
        int vaild = 0;
        if (person.isValid) vaild = 1;
        else vaild = 0;
        String sql = "INSERT INTO person values("
                + "'" + person.name + "'"
                + ", '" + person.kind + "'"
                + ", '" + person.gender + "'"
                + ", '" + person.minNumFirst + "'"
                + ", '" + person.minNumLast + "'"
                + ", '" + person.enrollDate + "'"
                + ", '" + person.filePath + "'"
                + ", " + person.age + ""
                + ", " + person.birthYear + ""
                + ", " + vaild + ""
                + ")";
        db.execSQL(sql);
    }

    public void removeDB(String filePath) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE from person where filePath =" + "'" + filePath + "'";
        db.execSQL(sql);
    }

    public ArrayList<Person> getAllPersonData() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Person> people = new ArrayList<>();
        String sql1 = "Select * from person order by filePath";
        try {
            Cursor recordset = db.rawQuery(sql1, null);
            recordset.moveToFirst();
            String str = "";

            do {
                Person person = new Person();
                person.name = recordset.getString(0);
                person.kind = recordset.getString(1);
                person.gender = recordset.getString(2);
                person.minNumFirst = recordset.getString(3);
                person.minNumLast = recordset.getString(4);
                person.enrollDate = recordset.getString(5);
                person.filePath = recordset.getString(6);
                person.age = recordset.getInt(7);
                person.birthYear = recordset.getInt(8);
                if (recordset.getInt(9) == 1) person.isValid = true;
                else person.isValid = false;

                people.add(person);
            } while (recordset.moveToNext());
            recordset.close();
        } catch (SQLiteException e) {
//                Toast.makeText((),"[Error]" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return people;
    }
}

