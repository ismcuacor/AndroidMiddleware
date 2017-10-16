package us.es.interfaces;

public interface Function<T> {
	
	T apply ();
	String getFunctionName();
	String getFunctionDescription();
	Integer getFunctionAccuracy();
	Float getFrecuency();
	void setFrecuency(Float frecuency);
	Float getMaxFrecuency();
	Float getMinFrecuency();
	
}