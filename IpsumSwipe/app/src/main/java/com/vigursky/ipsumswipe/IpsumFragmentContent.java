package com.vigursky.ipsumswipe;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vigursky on 06.10.2015.
 */

public class IpsumFragmentContent implements Parcelable {

    String content;

    public IpsumFragmentContent(String content){
        this.content = content;
    }

    private IpsumFragmentContent(Parcel in) {
        this.content = in.readString();
    }

    @Override
    public String toString() {
        return content;
    }

    public static final Creator<IpsumFragmentContent> CREATOR = new Creator<IpsumFragmentContent>() {
        @Override
        public IpsumFragmentContent createFromParcel(Parcel in) {
            return new IpsumFragmentContent(in);
        }

        @Override
        public IpsumFragmentContent[] newArray(int size) {
            return new IpsumFragmentContent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
    }
}
