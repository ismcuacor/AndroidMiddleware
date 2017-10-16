package com.eventsection;

import android.os.Parcel;
import android.os.Parcelable;

public class Value implements Parcelable{

	Integer entero;
	
	public Value (int i){
		entero = i;
	}
	
	public Value(Parcel in){
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(entero);
	}

	public void readFromParcel (Parcel in){
		entero = in.readInt();
	}
	
	public static final Parcelable.Creator<Value> CREATOR
    = new Parcelable.Creator<Value>() {
        public Value createFromParcel(Parcel in) {
            return new Value(in);
        }
 
        public Value[] newArray(int size) {
            return new Value[size];
        }
    };

}
