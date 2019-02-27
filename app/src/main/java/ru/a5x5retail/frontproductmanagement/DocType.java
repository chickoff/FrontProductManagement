package ru.a5x5retail.frontproductmanagement;

import android.support.v7.app.AppCompatActivity;
import ru.a5x5retail.frontproductmanagement.configuration.Constants;

public class DocType<T extends AppCompatActivity> {




    public DocType(){

    }


    private Class<T> classOfActivity;
    public Class<T> getClassOfActivity() {
        return classOfActivity;
    }
    public void setClassOfActivity(Class<T> classOfActivity) {

        this.classOfActivity = classOfActivity;
    }


    private Constants.TypeOfDocument typeOfDocument;
    public Constants.TypeOfDocument getTypeOfDocument() {
        return typeOfDocument;
    }
    public void setTypeOfDocument(Constants.TypeOfDocument typeOfDocument) {
        this.typeOfDocument = typeOfDocument;
    }


    private String name;

    private String shortName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
