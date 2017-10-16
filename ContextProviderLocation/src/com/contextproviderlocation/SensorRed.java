package com.contextproviderlocation;


import com.contextproviderlocation.interfaces.ISensor;

public class SensorRed implements ISensor {
	
	private String sensorName;
	private String sensorDescription;
	private Float maxFrecuency;
	private Float minFrecuency;

	public SensorRed(){
		this.sensorName = "Proveedor de localización de Red ";   
		this.sensorDescription = "Localizador mediante la red de tu proveedor o red wifi. Más inexacto que GPS. Consume menos batería.";
		this.maxFrecuency = 10f;
		this.minFrecuency = 20f;
	}
	
	public SensorRed(String sensorName, String sensorDescription, Float maxFrecuency, Float minFrecuency){
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
		return 60.96f;
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
