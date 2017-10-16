package com.contextproviderlocation.interfaces;


public interface IFunction<T> {
	
	T apply ();
	String getFunctionName();
	String getFunctionDescription();
	Integer getFunctionAccuracy();
	Float getFrecuency();
	void setFrecuency(Float frecuency);
	Float getMaxFrecuency();
	Float getMinFrecuency();
	
}