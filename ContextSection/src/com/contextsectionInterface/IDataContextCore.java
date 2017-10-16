package com.contextsectionInterface;

import java.util.List;


public interface IDataContextCore {

	public String getIdContextProvider();

	public List<String> getPermission();

	public float getMinFrequency();

	public List<String> getSensor();

	public String getDescription();

	public String getIdFunction();

	public float getMaxFrequency();

	public String getType();

	public String getdescriptionFunction();

	public float getFrequency();
	
	public Long getTimeStamp();
	
	Float getAccuracy();

	Object getValue();

}