package ru.a5x5retail.frontproductmanagement.сheckinglist;

import android.support.v7.app.AppCompatActivity;

public class DocType<T extends AppCompatActivity> {
    private String name;

    public DocType(){

    }

    public Class<T> getClassOfActivity() {
        return classOfActivity;
    }
    public void setClassOfActivity(Class<T> classOfActivity) {

        this.classOfActivity = classOfActivity;
    }
    private Class<T> classOfActivity;

}
