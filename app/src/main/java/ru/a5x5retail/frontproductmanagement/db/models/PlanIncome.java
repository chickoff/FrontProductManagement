package ru.a5x5retail.frontproductmanagement.db.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PlanIncome implements Parcelable {

        public String planIncomeGuid;
        public String numDoc;
        public String dateDoc;
        public String qty;
        public int validDoc;

        public PlanIncome() {

        }

        protected PlanIncome(Parcel in) {
                planIncomeGuid = in.readString();
                numDoc = in.readString();
                dateDoc = in.readString();
                qty = in.readString();
                validDoc = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(planIncomeGuid);
                dest.writeString(numDoc);
                dest.writeString(dateDoc);
                dest.writeString(qty);
                dest.writeInt(validDoc);
        }

        @Override
        public int describeContents() {
                return 0;
        }

        public static final Creator<PlanIncome> CREATOR = new Creator<PlanIncome>() {
                @Override
                public PlanIncome createFromParcel(Parcel in) {
                        return new PlanIncome(in);
                }

                @Override
                public PlanIncome[] newArray(int size) {
                        return new PlanIncome[size];
                }
        };
}
