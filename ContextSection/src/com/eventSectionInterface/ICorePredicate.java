package com.eventSectionInterface;

import com.contextsectionInterface.IDataContextCore;

public interface ICorePredicate {
	
//	/*
//	 * String predicateName
//	 * String predicateDescription
//	 */
//	
//	String getName();
//	
//	String getDescription();
	
	Boolean apply(Object value, IDataContextCore data);
}
