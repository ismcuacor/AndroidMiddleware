package com.application;

import com.aggregationsectionInterface.IAggregate;

import android.os.Parcelable;


public interface IApplicationInformation extends Parcelable{
	IAggregate getAggregate ();
	String getType ();
	String getPermission();
}
