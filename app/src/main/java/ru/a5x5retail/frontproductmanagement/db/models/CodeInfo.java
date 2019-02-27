package ru.a5x5retail.frontproductmanagement.db.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CodeInfo implements Parcelable {
    public int code;
    public String nameLong;

    public CodeInfo() {
    }

    protected CodeInfo(Parcel in) {
        code = in.readInt();
        nameLong = in.readString();
    }

    public static final Creator<CodeInfo> CREATOR = new Creator<CodeInfo>() {
        @Override
        public CodeInfo createFromParcel(Parcel in) {
            return new CodeInfo(in);
        }

        @Override
        public CodeInfo[] newArray(int size) {
            return new CodeInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(nameLong);
    }
}
