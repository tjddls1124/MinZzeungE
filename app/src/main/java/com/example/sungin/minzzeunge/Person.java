package com.example.sungin.minzzeunge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SungIn on 2017-06-11.
 */

public class Person implements Parcelable {
    String kind;
    String name;
    String gender;
    String minNumFirst;
    String minNumLast;
    String enrollDate;
    String filePath;
    int age;
    int birthYear;

    public Person(String filePath, String kind, String name, String minNumFirst, String minNumLast, String enrollDate) {
        this.filePath = filePath;
        this.kind = kind;
        this.name = name;
        this.minNumFirst = minNumFirst;
        this.minNumLast = minNumLast;
        this.enrollDate = enrollDate;

        /*
        주민등록 번호로 age와 gender 판별하여 autoSetting
         */
        if (this.minNumLast == null) gender = "";
        else {
            if (Integer.parseInt(minNumLast.substring(0, 1)) % 2 == 1) gender = "남";
            else gender = "여";
        }
        if (this.minNumFirst == null) {
            birthYear = 0;
            age = 0;
        } else {
            this.birthYear = Integer.parseInt("19"+minNumFirst.substring(0, 2));
            this.age = 2017 - birthYear + 1;
        }


    }

    public Person() {
        this.kind = null;
        this.name = null;
        this.gender = null;
        this.minNumFirst = null;
        this.minNumLast = null;
        this.enrollDate = null;
        this.filePath = null;
        this.age = 0;
        this.birthYear = 0;
    }

    public void copyPerson(Person p){
        this.filePath = p.filePath;
        this.kind = p.kind;
        this.name = p.name;
        this.minNumFirst = p.minNumFirst;
        this.minNumLast = p.minNumLast;
        this.enrollDate = p.enrollDate;

    }

    protected Person(Parcel in) {
        filePath = in.readString();
        kind = in.readString();
        name = in.readString();
        gender = in.readString();
        minNumFirst = in.readString();
        minNumLast = in.readString();
        enrollDate = in.readString();
        age = in.readInt();
        birthYear = in.readInt();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filePath);
        dest.writeString(kind);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(minNumFirst);
        dest.writeString(minNumLast);
        dest.writeString(enrollDate);
        dest.writeInt(age);
        dest.writeInt(birthYear);
    }
}
