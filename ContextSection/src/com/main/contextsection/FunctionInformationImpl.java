package com.main.contextsection;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.contextsectionInterface.IFunctionInformation;

public class FunctionInformationImpl implements IFunctionInformation {

	private String name;
	private String description;
	private Float frequency;
	private Float maxFrequency;
	private Float minFrequency;
	private List<String> sensors;

	public FunctionInformationImpl() {
		name = "";
		description = "";
		frequency = 0f;
		maxFrequency = 0f;
		minFrequency = 0f;
		sensors = new ArrayList<String>();
	}
	
	public FunctionInformationImpl(Parcel in) {
		sensors = new ArrayList<String>();
		readFromParcel(in);
	}

	public FunctionInformationImpl(String pName, String pDescription,
			Float pFrequency, Float pmaxFrequency, Float pminFrequency,
			List<String> pSensors) {
		name = pName;
		description = pDescription;
		frequency = pFrequency;
		maxFrequency = pmaxFrequency;
		minFrequency = pminFrequency;
		sensors = new ArrayList<String>(pSensors);
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Float getFrequency() {
		return this.frequency;
	}

	public Float getMaxFrequency() {
		return this.maxFrequency;
	}

	public Float getMinFrequency() {
		return this.minFrequency;
	}

	public List<String> getSensors() {
		return this.sensors;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(description);
		dest.writeFloat(frequency);
		dest.writeFloat(maxFrequency);
		dest.writeFloat(minFrequency);
		dest.writeList(sensors);
	}
	
	public void readFromParcel (Parcel in){
		name = in.readString(); 
		description = in.readString();;
		frequency = in.readFloat();
		maxFrequency = in.readFloat();
		minFrequency = in.readFloat();
		
		in.readList(sensors, FunctionInformationImpl.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<FunctionInformationImpl> CREATOR
    = new Parcelable.Creator<FunctionInformationImpl>() {
        public FunctionInformationImpl createFromParcel(Parcel in) {
            return new FunctionInformationImpl(in);
        }
 
        public FunctionInformationImpl[] newArray(int size) {
            return new FunctionInformationImpl[size];
        }
    };
}
