package com.contextproviderlocation;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.contextproviderlocation.interfaces.IFunction;

@SuppressWarnings("hiding")
public class FunctionLocation<Location> implements IFunction<Location> {

	private String functionName;
	private String functionDescription;
	private Float frecuency;
	private Float maxFrecuency;
	private Float minFrecuency;
	private static Context context;

	public FunctionLocation() {
		functionName = "GeoFunction";
		functionDescription = "Función que nos calcula la localización actual del dispositivo";
		frecuency = 0f;
		maxFrecuency = 0f;
		minFrecuency = 0f;
	}

	public FunctionLocation(String functionName, String functionDescription,
			Float frecuency, Float maxFrecuency, Float minFrecuency) {
		this.functionName = functionName;
		this.functionDescription = functionDescription;
		this.frecuency = frecuency;
		this.maxFrecuency = maxFrecuency;
		this.minFrecuency = minFrecuency;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Location apply() {
		
		LocationManager locman = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		Location location = null;
		if (!locman.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Log.i("NO", "No estoy conectado");
		} else {
			location = (Location) locman
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			LocationListener locationListener = new LocationListener() {

				public void onProviderDisabled(String provider) {

				}

				public void onProviderEnabled(String provider) {

				}

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					Log.i("LocAndroid", "Provider Status: " + status);
					
				}

				public void onLocationChanged(android.location.Location location) {
					Double latitude = location.getLatitude();
					Double longitude = location.getLongitude();
					Double altitude = location.getAltitude();
					ActivityMain.getViewAltitude().setText(altitude.toString());
					ActivityMain.getViewLocation().setText(latitude.toString()+", "+ longitude.toString());
					Log.i("location", latitude.toString()+", "+ longitude.toString());
					

				}
			};
			
			locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 10,
					locationListener);

		}
		return location;
	}
	
	public static void setContext(Context pcontext){
		context = pcontext;
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
