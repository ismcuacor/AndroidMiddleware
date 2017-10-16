package us.es.contextProvider;

import us.es.interfaces.Sensor;

public class SensorTimer implements Sensor {
	
	private String sensorName;
	private String sensorDescription;
	private Float maxFrecuency;
	private Float minFrecuency;

	public SensorTimer(){
		this.sensorName = "Sensor hora";   
		this.sensorDescription = "";
		this.maxFrecuency = 5f;
		this.minFrecuency = 15f;
	}
	
	public SensorTimer(String sensorName, String sensorDescription, Float maxFrecuency, Float minFrecuency){
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
		return 0f;
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
