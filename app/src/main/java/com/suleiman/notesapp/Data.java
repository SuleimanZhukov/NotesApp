package com.suleiman.notesapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    private String mName;
    private String mDetails;
    private String mDate;

    protected Data(Parcel in) {
        mName = in.readString();
        mDetails = in.readString();
        mDate = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDetails);
        dest.writeString(mDate);
    }
}
