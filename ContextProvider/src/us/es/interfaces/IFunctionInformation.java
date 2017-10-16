package us.es.interfaces;

import java.util.List;

import android.os.Parcelable;

public interface IFunctionInformation extends Parcelable {
	
	String getName();
	String getDescription();
	Float getFrequency();
	Float getMaxFrequency();
	Float getMinFrequency();
	List<String> getSensors();
}
