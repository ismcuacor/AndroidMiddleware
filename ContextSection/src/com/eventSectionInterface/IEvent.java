package com.eventSectionInterface;

import android.os.Parcelable;

public interface IEvent extends Parcelable {
	
	String getIdContext();
	
	String getIdFunction();
	
	String getCorePredicate();
	
	Object getValue();
	
}
