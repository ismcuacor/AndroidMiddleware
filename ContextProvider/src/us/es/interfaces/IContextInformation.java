package us.es.interfaces;

import java.util.List;

import com.main.contextsection.FunctionInformationImpl;

import android.os.Parcelable;

public interface IContextInformation extends Parcelable{

	List<FunctionInformationImpl> getFunctions();
	String getDescription();
	String getType();
	List<String> getPermissions();
}
