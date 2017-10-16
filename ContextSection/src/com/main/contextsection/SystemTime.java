package com.main.contextsection;

import android.os.Parcel;
import android.os.Parcelable;

public class SystemTime implements Parcelable{
	String time;
	public SystemTime(String time) {
		this.time = time;
	}
	
	public SystemTime(Parcel in) {
		readFromParcel(in);
	}

	public String getTime(){
		return time;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(time);
	}
	
	public void readFromParcel (Parcel in){
		time = in.readString(); 
	}
	
	public static final Parcelable.Creator<SystemTime> CREATOR
    = new Parcelable.Creator<SystemTime>() {
        public SystemTime createFromParcel(Parcel in) {
            return new SystemTime(in);
        }
 
        public SystemTime[] newArray(int size) {
            return new SystemTime[size];
        }
    };

}
