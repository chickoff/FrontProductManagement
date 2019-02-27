package ru.a5x5retail.frontproductmanagement.db.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckingListHead implements Parcelable {

    public String Guid;
    public String StatusID;
    public String TypeDocID;
    public String RRGUID;
    public String NameDoc;
    public String Note;
    public String IMEI;
    public String LDM;
    public String LDC;

    public CheckingListHead(){

    }
    protected CheckingListHead(Parcel in) {
        Guid = in.readString();
        StatusID = in.readString();
        TypeDocID = in.readString();
        RRGUID = in.readString();
        NameDoc = in.readString();
        Note = in.readString();
        IMEI = in.readString();
        LDM = in.readString();
        LDC = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Guid);
        dest.writeString(StatusID);
        dest.writeString(TypeDocID);
        dest.writeString(RRGUID);
        dest.writeString(NameDoc);
        dest.writeString(Note);
        dest.writeString(IMEI);
        dest.writeString(LDM);
        dest.writeString(LDC);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CheckingListHead> CREATOR = new Creator<CheckingListHead>() {
        @Override
        public CheckingListHead createFromParcel(Parcel in) {
            return new CheckingListHead(in);
        }

        @Override
        public CheckingListHead[] newArray(int size) {
            return new CheckingListHead[size];
        }
    };
}
