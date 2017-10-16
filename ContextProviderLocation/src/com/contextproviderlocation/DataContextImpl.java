package com.contextproviderlocation;

import com.contextproviderlocation.interfaces.IDataContext;

public class DataContextImpl implements IDataContext {
	
	private String idFunction;
	private long timeStamp;
	private Integer accuracy;
	private String value;

	public DataContextImpl() {

	}

	public DataContextImpl(String idf, Integer acy, String val) {

		idFunction = idf;
		accuracy = acy;
		value = val;
		timeStamp = System.currentTimeMillis();

	}

	@Override
	public String getIdFunction() {
		return idFunction;
	}

	@Override
	public Long getTimeStamp() {
		return timeStamp;
	}

	@Override
	public Integer getAccuracy() {
		return accuracy;
	}

	@Override
	public String getValue() {
		return value;
	}
	
}
