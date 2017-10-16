package com.main.contextsection;

import android.os.Parcel;
import android.os.Parcelable;
import com.contextsectionInterface.IDataContext;

public class DataContextImpl implements IDataContext {

	private String idFunction;
	private long timeStamp;
	private Float accuracy;
	private Parcelable value;

	public DataContextImpl() {

	}

	public DataContextImpl(String idf, Float acy, Parcelable val) {
		idFunction = idf;
		accuracy = acy;
		value = val;
		timeStamp = System.currentTimeMillis();
	}
	
	public DataContextImpl(Parcel in) {
		this.readFromParcel(in);
	}

	@Override
	public String getIdFunction() {
		return idFunction;
	}

	@Override
	public Long getTimeStamp() {
		return timeStamp;
	}

	@Override
	public Float getAccuracy() {
		return accuracy;
	}

	@Override
	public Parcelable getValue() {
		return value;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(idFunction);
		dest.writeFloat(accuracy);
		dest.writeParcelable(value, 0);
		dest.writeLong(timeStamp);
	}
	
	public void readFromParcel (Parcel in){
		idFunction = in.readString(); 
		accuracy = in.readFloat();
		value = in.readParcelable(this.getClass().getClassLoader());
		timeStamp = in.readLong();
	}
	
	public static final Parcelable.Creator<DataContextImpl> CREATOR
    = new Parcelable.Creator<DataContextImpl>() {
        public DataContextImpl createFromParcel(Parcel in) {
            return new DataContextImpl(in);
        }
 
        public DataContextImpl[] newArray(int size) {
            return new DataContextImpl[size];
        }
    };

}
