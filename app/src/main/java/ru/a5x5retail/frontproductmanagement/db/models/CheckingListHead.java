package ru.a5x5retail.frontproductmanagement.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class CheckingListHead implements Parcelable {

    public String Guid;
    public String StatusID;
    public String TypeDocID;
    public String sourceGuid;
    public String NameDoc;
    public String Note;
    public String IMEI;
    public String LDM;
    public String LDC;
    public int errorCode;
    public String errorMessage;
    public BigDecimal summ;
    public String contractorName;
    public BigDecimal summVat;

    public CheckingListHead(){

    }

    protected CheckingListHead(Parcel in) {
        Guid = in.readString();
        StatusID = in.readString();
        TypeDocID = in.readString();
        sourceGuid = in.readString();
        NameDoc = in.readString();
        Note = in.readString();
        IMEI = in.readString();
        LDM = in.readString();
        LDC = in.readString();
        errorCode = in.readInt();
        errorMessage = in.readString();
        Long summLong = in.readLong();
        summ = new BigDecimal(summLong == 0 ? 0 : summLong / 10000);
        contractorName = in.readString();
        Long summVatLong = in.readLong();
        summVat = new BigDecimal(summVatLong == 0 ? 0 : summVatLong / 10000);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Guid);
        dest.writeString(StatusID);
        dest.writeString(TypeDocID);
        dest.writeString(sourceGuid);
        dest.writeString(NameDoc);
        dest.writeString(Note);
        dest.writeString(IMEI);
        dest.writeString(LDM);
        dest.writeString(LDC);
        dest.writeInt(errorCode);
        dest.writeString(errorMessage);
        Long summLong = Long.decode(summ.multiply(new BigDecimal(10000)).setScale(0, RoundingMode.FLOOR).toString());
        dest.writeLong(summLong);
        dest.writeString(contractorName);
        Long summVatLong = Long.decode(summVat.multiply(new BigDecimal(10000)).setScale(0, RoundingMode.FLOOR).toString());
        dest.writeLong(summVatLong);

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
