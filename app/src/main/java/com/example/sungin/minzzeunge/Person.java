package com.example.sungin.minzzeunge;

import android.content.Intent;

/**
 * Created by SungIn on 2017-06-11.
 */

public class Person {
    String name;
    String gender;
    String minNumFirst;
    String minNumLast;
    String enrollDate;
    int age;
    int birthYear;

    public Person(String name, String minNumFirst, String minNumLast, String enrollDate) {
        this.name = name;
        this.minNumFirst = minNumFirst;
        this.minNumLast = minNumLast;
        this.enrollDate = enrollDate;

        /*
        주민등록 번호로 age와 gender 판별
         */

        this.birthYear = Integer.parseInt(minNumFirst.substring(0,2));
        this.age = 2017 - birthYear +1 ;
        if( Integer.parseInt(minNumLast.substring(0,1)) % 2 == 1) gender="Male";
        else gender = "Female";
    }


}
