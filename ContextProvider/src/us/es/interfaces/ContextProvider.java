package us.es.interfaces;

import java.security.acl.Permission;
import java.util.Map;
import java.util.Set;
import us.es.enumerados.ContextType;


public interface ContextProvider{
	
	Function<?> getFunctionByName(String function);
	Set<Function<?>> getAllFunctions();
	String getDescription();
	String getName();
	ContextType getContextType();
	Set<Permission> getPermissions();
	Set<Sensor> getAllSensors();
	Float getFrecuency();
	Map<String, Object> onChange();
	Boolean isAlive();
	void correctInstalation();
	Boolean sendData(String idPaquete, IDataContext info);

}