package com.example.mocoapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class GroupParcel implements Parcelable {
    private String title;
    private String content;
    private int headCnt;
    private String date;
    private String time;
    private String location;
    private String groupID;

    public GroupParcel(String title, String content, int headCnt, String date, String time, String location, String groupID) {
        this.title = title;
        this.content = content;
        this.headCnt = headCnt;
        this.date = date;
        this.time = time;
        this.location = location;
        this.groupID = groupID;
    }

    protected GroupParcel(Parcel in) {
        title = in.readString();
        content = in.readString();
        headCnt = in.readInt();
        date = in.readString();
        time = in.readString();
        location = in.readString();
        groupID = in.readString();
    }

    public static final Creator<GroupParcel> CREATOR = new Creator<GroupParcel>() {
        @Override
        public GroupParcel createFromParcel(Parcel in) {
            return new GroupParcel(in);
        }

        @Override
        public GroupParcel[] newArray(int size) {
            return new GroupParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeInt(headCnt);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeString(location);
        parcel.writeString(groupID);
    }
}
