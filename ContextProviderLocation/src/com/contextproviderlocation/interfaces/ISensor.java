package com.contextproviderlocation.interfaces;

public interface ISensor {
	
	String getSensorName();
	String getSensorDescription();
	Float getSensorAccuracy();
	Float getSensorMaxFrecuency();
	Float getSensorMinFrecuency();
}