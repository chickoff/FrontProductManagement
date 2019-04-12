package ru.a5x5retail.frontproductmanagement.db.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ContractorExtendedInfo implements Parcelable {
    public String contractorGuid;
    public String contractorName;
    public String agreementGuid;
    public int edi;
    public int ediTp;
    public int rpbpp;
    public int cz;
    public int dp;


    public ContractorExtendedInfo() {

    }

    protected ContractorExtendedInfo(Parcel in) {
        contractorGuid = in.readString();
        contractorName = in.readString();
        agreementGuid = in.readString();
        edi = in.readInt();
        ediTp = in.readInt();
        rpbpp = in.readInt();
        cz = in.readInt();
        dp = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contractorGuid);
        dest.writeString(contractorName);
        dest.writeString(agreementGuid);
        dest.writeInt(edi);
        dest.writeInt(ediTp);
        dest.writeInt(rpbpp);
        dest.writeInt(cz);
        dest.writeInt(dp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContractorExtendedInfo> CREATOR = new Creator<ContractorExtendedInfo>() {
        @Override
        public ContractorExtendedInfo createFromParcel(Parcel in) {
            return new ContractorExtendedInfo(in);
        }

        @Override
        public ContractorExtendedInfo[] newArray(int size) {
            return new ContractorExtendedInfo[size];
        }
    };
}
