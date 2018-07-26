package com.example.dell1.mainideaapp;

import java.util.ArrayList;
import java.util.List;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class MyGroups implements Parcelable{

    @NonNull
    @PrimaryKey
    private String timeofcreation;

    private String groupName;

    @TypeConverters(Converters.class)
    private List<GroupMembers>  groupMembersArrayList;

    @Ignore
    public MyGroups() {
    }

    public MyGroups(String groupName, String timeofcreation, List<GroupMembers> groupMembersArrayList){
        this.groupName = groupName;
        this.timeofcreation = timeofcreation;
        this.groupMembersArrayList=groupMembersArrayList;
    }

    protected MyGroups(Parcel in) {
        timeofcreation = in.readString();
        groupName = in.readString();
        groupMembersArrayList = in.createTypedArrayList(GroupMembers.CREATOR);
    }

    public static final Creator<MyGroups> CREATOR = new Creator<MyGroups>() {
        @Override
        public MyGroups createFromParcel(Parcel in) {
            return new MyGroups(in);
        }

        @Override
        public MyGroups[] newArray(int size) {
            return new MyGroups[size];
        }
    };

    public void setTimeofcreation(@NonNull String timeofcreation) {
        this.timeofcreation = timeofcreation;
    }


    public void setGroupMembersArrayList(List<GroupMembers> groupMembersArrayList) {
        this.groupMembersArrayList = groupMembersArrayList;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public List<GroupMembers> getGroupMembersArrayList() {

        return groupMembersArrayList;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getTimeofcreation() {
        return timeofcreation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(timeofcreation);
        parcel.writeString(groupName);
        parcel.writeTypedList(groupMembersArrayList);
    }
}
