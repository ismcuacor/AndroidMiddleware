package com.contextproviderlocation;

import com.contextproviderlocation.interfaces.ISensor;

public class SensorGPS implements ISensor {
	
	private String sensorName;
	private String sensorDescription;
	private Float maxFrecuency;
	private Float minFrecuency;

	public SensorGPS(){
		this.sensorName = "Sensor GPS";   
		this.sensorDescription = "Sensor de geolocalización más exacto con una precisión de 4 a 40 metros. Consume más batería";
		this.maxFrecuency = 1f;
		this.minFrecuency = 10f;
	}
	
	public SensorGPS(String sensorName, String sensorDescription, Float maxFrecuency, Float minFrecuency){
		this.sensorName = sensorName;
		this.sensorDescription = sensorDescription;
		this.maxFrecuency = maxFrecuency;
		this.minFrecuency = minFrecuency;
	}
	@Override
	public String getSensorName() {
		return this.sensorName;
	}

	@Override
	public String getSensorDescription() {
		return this.sensorDescription;
	}
	
	public void setSensorDescription(String description){
		this.sensorDescription = description;
	}

	@Override
	public Float getSensorAccuracy() {
		return (4f+40)/2;
	}

	@Override
	public Float getSensorMaxFrecuency() {
		return this.maxFrecuency;
	}

	@Override
	public Float getSensorMinFrecuency() {
		return this.minFrecuency;
	}

}
