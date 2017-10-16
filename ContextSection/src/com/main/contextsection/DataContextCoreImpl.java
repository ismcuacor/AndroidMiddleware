package com.main.contextsection;

import java.util.List;

import com.contextsectionInterface.IDataContextCore;

public class DataContextCoreImpl implements IDataContextCore {
	private String idContextProvider;
	private List<String> permission;
	private float minFrequency;
	private List<String> sensor;
	private String description;
	private float maxFrequency;
	private String type;
	private String descriptionFunction;
	private float frequency;

	private String idFunction;
	private long timeStamp;
	private Float accuracy;
	private Object value;

	
	public DataContextCoreImpl(String packag){
		super();
		idContextProvider = packag;
	}

	public DataContextCoreImpl(String packag, String idf, Float acy, Object val){
		idFunction = idf;
		accuracy = acy;
		value = val;
		timeStamp = System.currentTimeMillis();
		idContextProvider =  packag;
	}
	
	
	/* (non-Javadoc)
	 * @see com.contextsection.IDataContextCore#getIdContextProvider()
	 */
	@Override
	public String getIdContextProvider(){
		return idContextProvider;
	}
	
	public String toString(){
		return "Identificador es:" + idContextProvider;
	}

	@Override
	public List<String> getPermission() {
		return permission;
	}

	@Override
	public float getMinFrequency() {
		return minFrequency;
	}

	@Override
	public List<String> getSensor() {
		return sensor;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public float getMaxFrequency() {
		return maxFrequency;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getdescriptionFunction() {
		return descriptionFunction;
	}

	@Override
	public float getFrequency() {
		return frequency;
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
	public Float getAccuracy() {
		return accuracy;
	}

	@Override
	public Object getValue() {
		return value;
	}

}
