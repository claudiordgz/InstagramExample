package dev.claudiordgz.android.sliderlib.views.custom_views.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android1 on 1/25/2015.
 */
public class ListOfTabs implements Parcelable {

    private List<SingleTab> mTabs = new ArrayList<>();

    public ListOfTabs(SingleTab ... values) {
        for(int i = 0; i !=  values.length; ++i){
            mTabs.add(values[i]);
        }
    }

    public List<SingleTab> getTabs() {
        return mTabs;
    }

    public void setTabs(List<SingleTab> tabs) {
        this.mTabs = tabs;
    }

    protected ListOfTabs(Parcel in) {
        if (in.readByte() == 0x01) {
            mTabs = new ArrayList<SingleTab>();
            in.readList(mTabs, SingleTab.class.getClassLoader());
        } else {
            mTabs = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mTabs == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mTabs);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<ListOfTabs> CREATOR = new Creator<ListOfTabs>() {
        @Override
        public ListOfTabs createFromParcel(Parcel in) {
            return new ListOfTabs(in);
        }

        @Override
        public ListOfTabs[] newArray(int size) {
            return new ListOfTabs[size];
        }
    };
}