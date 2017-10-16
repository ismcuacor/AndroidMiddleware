package com.main.contextsection;

import java.util.ArrayList;
import java.util.List;

import com.contextsectionInterface.IContextInformation;
import com.contextsectionInterface.IContextInformationCore;
import com.contextsectionInterface.IFunctionInformation;

public class ContextInformationCoreImpl implements IContextInformationCore {

	private String name;
	private String description;
	private List<FunctionInformationImpl> functions;
	private String type;
	private List<String> permissions;
	// Lo nuevo de la interfaz...
	private List<String> sensors;
	private Float frequency;
	private Float maxFrequency;
	private Float minFrequency;

	public ContextInformationCoreImpl() {
		name = "";
		functions = new ArrayList<FunctionInformationImpl>();
		type = "";
		permissions = new ArrayList<String>();
		sensors = new ArrayList<String>();
		frequency = 0f;
		maxFrequency = 0f;
		minFrequency = 0f;
	}

	public ContextInformationCoreImpl(String pName, List<FunctionInformationImpl> pFunctions,
			String pType, List<String> pPermissions, List<String> pSensors,
			Float pFrequency, Float pmaxFrequency, Float pminFrequency) {
		name = pName;
		functions = new ArrayList<FunctionInformationImpl>(pFunctions);
		type = pType;
		permissions = new ArrayList<String>(pPermissions);
		sensors = new ArrayList<String>(pSensors);
		frequency = pFrequency;
		maxFrequency = pmaxFrequency;
		minFrequency = pminFrequency;
	}
	
	public ContextInformationCoreImpl(String idPaquete, IContextInformation context) {
		name = idPaquete;
		//TODO Clonar los valores y Investigar en la frecuencia óptima
		description = context.getDescription();
		functions = context.getFunctions();
		type = context.getType();
		permissions = context.getPermissions();
		sensors = new ArrayList<String>();
		Float maxFrequencyAux;
		Float minFrequencyAux;
		maxFrequency = Float.MAX_VALUE;
		minFrequency = Float.MIN_VALUE;
		for (IFunctionInformation function:functions){
			sensors.addAll(function.getSensors());
			maxFrequencyAux = function.getMaxFrequency();
			minFrequencyAux = function.getMinFrequency();
			if (maxFrequency > maxFrequencyAux)
				maxFrequency = maxFrequencyAux;
			if (minFrequency < minFrequencyAux)
				minFrequency = minFrequencyAux;
		}
		frequency = (maxFrequency + minFrequency)/2;
	}

	@Override
	public String getName() {
		return this.name;
	}

	
	@Override
	public List<FunctionInformationImpl> getFunctions() {
		return this.functions;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public List<String> getPermissions() {
		return this.permissions;
	}

	// Métodos nuevos...

	@Override
	public List<String> getSensors() {
		return sensors;
	}

	@Override
	public float getFrequency() {
		return frequency;
	}

	@Override
	public float getMaxFrequency() {
		return maxFrequency;
	}

	@Override
	public float getMinFrequency() {
		return minFrequency;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
