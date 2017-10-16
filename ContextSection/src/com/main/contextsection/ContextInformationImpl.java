package com.main.contextsection;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.contextsectionInterface.IContextInformation;

public class ContextInformationImpl implements IContextInformation, Parcelable {

	private String description;
	private List<FunctionInformationImpl> functions;
	private String type;
	private List<String> permissions;

	public ContextInformationImpl() {
		functions = new ArrayList<FunctionInformationImpl>();
		type = "";
		permissions = new ArrayList<String>();
	}

	public ContextInformationImpl(String description, List<FunctionInformationImpl> pFunctions,
			String pType, List<String> pPermissions) {
		functions = new ArrayList<FunctionInformationImpl>(pFunctions);
		type = pType;
		permissions = new ArrayList<String>(pPermissions);
		this.description = description;
	}

	public ContextInformationImpl(Parcel in) {
		functions = new ArrayList<FunctionInformationImpl>();
		permissions = new ArrayList<String>();
		this.readFromParcel(in);
	}

	@Override
	public List<FunctionInformationImpl> getFunctions() {
		return this.functions;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public List<String> getPermissions() {
		return this.permissions;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(type);
		dest.writeString(description);
		dest.writeList(permissions);
		dest.writeTypedList(functions);
	}
	
	public void readFromParcel (Parcel in){
		type = in.readString(); 
		description = in.readString();
		in.readList(permissions, FunctionInformationImpl.class.getClassLoader());
		in.readTypedList(functions, FunctionInformationImpl.CREATOR);
	}
	
	public static final Parcelable.Creator<ContextInformationImpl> CREATOR
    = new Parcelable.Creator<ContextInformationImpl>() {
        public ContextInformationImpl createFromParcel(Parcel in) {
            return new ContextInformationImpl(in);
        }
 
        public ContextInformationImpl[] newArray(int size) {
            return new ContextInformationImpl[size];
        }
    };

}
