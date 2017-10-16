package com.eventSectionInterface;

public interface IEventBackup<T> {
	
	String getIdContext();
	
	String getIdFunction();
	
	ICorePredicate getCorePredicate();
	
	T getValue();
	
	Boolean evaluateEvent();
	
}
