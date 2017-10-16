package com.contextproviderlocation.interfaces;

import java.security.acl.Permission;
import java.util.Map;
import java.util.Set;

import com.enumerados.ContextType;


public interface IContextProvider{
	
	IFunction<?> getFunctionByName(String function);
	Set<IFunction<?>> getAllFunctions();
	String getDescription();
	String getName();
	ContextType getContextType();
	Set<Permission> getPermissions();
	Set<ISensor> getAllSensors();
	Float getFrecuency();
	Map<String, Object> onChange();
	Boolean isAlive();
	void correctInstalation();
	Boolean sendData(Map<String,Object> info);

}