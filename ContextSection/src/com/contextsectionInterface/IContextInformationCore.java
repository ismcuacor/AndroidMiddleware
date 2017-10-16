package com.contextsectionInterface;

import java.util.List;

import com.main.contextsection.FunctionInformationImpl;

public interface IContextInformationCore {

	String getName();
	List<FunctionInformationImpl> getFunctions();
	String getDescription();
	String getType();
	List<String> getPermissions();
	List<String> getSensors();
	float getFrequency();
	float getMaxFrequency();
	float getMinFrequency();
	
}
