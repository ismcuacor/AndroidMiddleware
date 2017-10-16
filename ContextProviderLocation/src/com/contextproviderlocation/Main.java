package com.contextproviderlocation;


import java.security.acl.Permission;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.contextproviderlocation.interfaces.IContextProvider;
import com.contextproviderlocation.interfaces.IDataContext;
import com.contextproviderlocation.interfaces.IFunction;
import com.contextproviderlocation.interfaces.ISensor;
import com.enumerados.ContextType;

public class Main extends Service implements IContextProvider {

	private Set<IFunction<?>> functions;
	private String description;
	private String name;
	private ContextType type;
	private Set<Permission> permissions;
	private Float frecuency;
	private Set<ISensor> sensors;
	private Integer i = 0;

	public Main() {
		functions = new HashSet<IFunction<?>>();
		IFunction<Location> function = new FunctionLocation<Location>();
		functions.add(function);
		description = "Context Provider que provee de la localización del dispositivo";
		name = "ContextLocation";
		type = ContextType.Tipo1;
		permissions = new HashSet<Permission>();
		frecuency = 10f;
		sensors = new HashSet<ISensor>();
		ISensor sensor = new SensorGPS();
		sensors.add(sensor);

	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public void onCreate() {
		super.onCreate();
		@SuppressWarnings("unchecked")
		IFunction<Location> functionLocation = (IFunction<Location>) getFunctionByName("GeoFunction");
		FunctionLocation.setContext(getBaseContext());
		Location l = functionLocation.apply();

		Intent intentContext = new Intent(this, ActivityMain.class);
		intentContext.putExtra("location", l);
		intentContext.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intentContext);
		Log.i("Lo escribo", "ole");
		this.correctInstalation();

	}

	public void onDestroy() {
		super.onDestroy();
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (i != 0) {
			i = 0;
			@SuppressWarnings("unchecked")
			IFunction<Location> functionLocation = (IFunction<Location>) getFunctionByName("GeoFunction");
			FunctionLocation.setContext(getBaseContext());
			Location l = functionLocation.apply();
			IDataContext data = new DataContextImpl("com.contextproviderlocation",0,l.toString());
			Map<String, Object> m = new HashMap<String, Object>();
			m.put(functionLocation.getFunctionName(), data);
			this.sendData(m);
		}
		i++;
		return START_NOT_STICKY;
	}

	@Override
	public IFunction<?> getFunctionByName(String functionName) {
		IFunction<?> function = null;
		for (IFunction<?> f : getAllFunctions()) {
			if (f.getFunctionName().equals(functionName)) {
				function = f;
				break;
			}
		}
		return function;
	}

	@Override
	public Set<IFunction<?>> getAllFunctions() {
		return functions;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ContextType getContextType() {
		return type;
	}

	@Override
	public Set<Permission> getPermissions() {
		return permissions;
	}

	@Override
	public Set<ISensor> getAllSensors() {
		return sensors;
	}

	@Override
	public Float getFrecuency() {
		return frecuency;
	}

	@Override
	public Map<String, Object> onChange() {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		IFunction<Location> functionLocation = (IFunction<Location>) getFunctionByName("GeoFunction");
		FunctionLocation.setContext(getBaseContext());
		map.put(functionLocation.getFunctionName(), functionLocation.apply());
		return map;
	}

	@Override
	public Boolean isAlive() {
		return true;
	}

	@Override
	public void correctInstalation() {
		String idPaquete = "com.contextproviderlocation";
		Intent intent = new Intent();
		intent.setClassName("com.main.contextsection",
				"com.main.contextsection.ContextSubscriptionImpl");
		intent.addCategory("android.intent.category.LAUNCHER");
		intent.putExtra("envio", idPaquete);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

	}

	@Override
	public Boolean sendData(Map<String, Object> info) {
		Intent intent = new Intent();
		intent.setClassName("com.main.contextsection",
				"com.main.contextsection.ContextUpdaterImpl");
		intent.addCategory("android.intent.category.LAUNCHER");
		intent.putExtra("info", info.toString());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

		return true;
	}
}
