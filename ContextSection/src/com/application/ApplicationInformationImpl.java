package com.application;

import com.aggregationsection.Aggregate;
import com.aggregationsectionInterface.IAggregate;

import android.os.Parcel;
import android.os.Parcelable;


public class ApplicationInformationImpl implements IApplicationInformation {
	Aggregate aggregate;
	String type;
	String permission;

	public ApplicationInformationImpl(Aggregate aggregate, String type, String permission) {
		this.aggregate = aggregate;
		this.type = type;
		this.permission = permission;
	}

	
	public ApplicationInformationImpl(Parcel in) {
		this.readFromParcel(in);
	}

	
	private void readFromParcel(Parcel in) {
		permission = in.readString();
		type = in.readString();
		aggregate = in.readParcelable(Aggregate.class.getClassLoader());
	}

	@Override
	public IAggregate getAggregate() {
		return aggregate;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(permission);
		dest.writeString(type);
		dest.writeParcelable(aggregate, 0);
	}

	@Override
	public String getType() {
		return type;
	}

	public String getPermission() {
		return permission;
	}
	public static final Parcelable.Creator<ApplicationInformationImpl> CREATOR
    = new Parcelable.Creator<ApplicationInformationImpl>() {
        public ApplicationInformationImpl createFromParcel(Parcel in) {
            return new ApplicationInformationImpl(in);
        }
 
        public ApplicationInformationImpl[] newArray(int size) {
            return new ApplicationInformationImpl[size];
        }
    };
}