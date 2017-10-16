package com.eventSectionInterface;

public interface IEventCore {
	
	String getIdContext();
	
	String getIdFunction();
	
	ICorePredicate getCorePredicate();
	
	Object getValue();

	Boolean evaluateEvent();
	
}
