package ru.a5x5retail.frontproductmanagement;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    private String shortName;
    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    private List<DocType> childDocList;
    public List<DocType> getChildDocs() {
        return childDocList;
    }
    public void setChildDocs(List<DocType> childDocList) {
        this.childDocList = childDocList;
    }

    private DocType parentDoc;
    public DocType getParentDoc() {
        return parentDoc;
    }
    public void setParentDoc(DocType parentDoc) {
        this.parentDoc = parentDoc;
    }

    private boolean isSuperPwdProtect;
    public boolean isSuperPwdProtect() {
        return isSuperPwdProtect;
    }
    public void setSuperPwdProtect(boolean superPwdProtect) {
        isSuperPwdProtect = superPwdProtect;
    }
}
