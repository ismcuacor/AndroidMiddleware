package us.es.contextProvider;

import java.util.Calendar;

import us.es.interfaces.Function;

public class FunctionTimer<Date> implements Function<Date> {

	private String functionName;
	private String functionDescription;
	private Float frecuency;
	private Float maxFrecuency;
	private Float minFrecuency;

	public FunctionTimer() {
		functionName = "TimerHora";
		functionDescription = "Función que nos envía la hora local";
		frecuency = 10f;
		maxFrecuency = 5f;
		minFrecuency = 15f;
	}

	public FunctionTimer(String functionName, String functionDescription,
			Float frecuency, Float maxFrecuency, Float minFrecuency) {
		this.functionName = functionName;
		this.functionDescription = functionDescription;
		this.frecuency = frecuency;
		this.maxFrecuency = maxFrecuency;
		this.minFrecuency = minFrecuency;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Date apply() {
		return (Date) Calendar.getInstance().getTime();
	}

	@Override
	public String getFunctionName() {
		return this.functionName;
	}

	@Override
	public String getFunctionDescription() {
		return this.functionDescription;
	}

	@Override
	public Integer getFunctionAccuracy() {
		return 0;
	}

	public Float getFrecuency() {
		return this.frecuency;
	}

	@Override
	public void setFrecuency(Float frecuency) {
		this.frecuency = frecuency;
	}

	@Override
	public Float getMaxFrecuency() {
		return this.maxFrecuency;
	}

	@Override
	public Float getMinFrecuency() {
		return this.minFrecuency;
	}

}
