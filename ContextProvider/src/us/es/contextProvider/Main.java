package us.es.contextProvider;

import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.Set;

import com.main.contextsection.ContextInformationImpl;
import com.main.contextsection.DataContextImpl;
import com.main.contextsection.FunctionInformationImpl;
import com.main.contextsection.SystemTime;

import us.es.enumerados.ContextType;
import us.es.interfaces.ContextProvider;
import us.es.interfaces.Function;
import us.es.interfaces.IContextInformation;
import us.es.interfaces.IDataContext;

import us.es.interfaces.Sensor;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Main extends Service implements ContextProvider {

	private Set<Function<?>> functions;
	private String description;
	private String name;
	private ContextType type;
	private Set<Permission> permissions;
	private Float frecuency;
	private Set<Sensor> sensors;
//	private Integer i = 0;

	public Main() {
		functions = new HashSet<Function<?>>();
		Function<Date> function = new FunctionTimer<Date>();
		functions.add(function);
		description = "Context Provider que provee la hora del dispositivo";
		name = "ContextTimer";
		type = ContextType.Tipo1;
		permissions = new HashSet<Permission>();
		frecuency = 10f;
		sensors = new HashSet<Sensor>();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public void onCreate() {
		super.onCreate();
		/*@SuppressWarnings("unchecked")
		 * Function<Date> functionTimer = (Function<Date>) getFunctionByName("TimerHora");
		 *
		 * Date d = functionTimer.apply(); Intent intentContext = new
		 * Intent(this, ActivityMain.class); intentContext.putExtra("timer", d);
		 * intentContext.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * startActivity(intentContext);
		 */
		
		this.correctInstalation();
	}

	public void onDestroy() {
		super.onDestroy();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		String accepted = intent.getStringExtra("Accepted");
		
		if (accepted != null) {
			if (accepted.equals("true")) {
				@SuppressWarnings("unchecked")
				Function<Date> functionTimer = (Function<Date>) getFunctionByName("TimerHora");
				Date d = functionTimer.apply();
				SystemTime time = new SystemTime(d.toString());
				IDataContext data = new DataContextImpl("Timer", 0f, time);
				
				this.sendData("us.es.contextProvider", data);
			}
		}

		return START_NOT_STICKY;
	}

	@Override
	public Function<?> getFunctionByName(String functionName) {
		Function<?> function = null;
		for (Function<?> f : getAllFunctions()) {
			if (f.getFunctionName().equals(functionName)) {
				function = f;
				break;
			}
		}
		return function;
	}

	@Override
	public Set<Function<?>> getAllFunctions() {
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
	public Set<Sensor> getAllSensors() {
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
		Function<Date> funcionHora = (Function<Date>) getFunctionByName("ContextTimer");
		map.put(funcionHora.getFunctionName(), funcionHora.apply());
		return map;
	}

	@Override
	public Boolean isAlive() {
		return true;
	}

	@Override
	public void correctInstalation() {
		String idPaquete = "us.es.contextProvider";
		List<String> sensors = new ArrayList<String>();
		List<String> permissions = new ArrayList<String>();
		List <FunctionInformationImpl> functionsList = new ArrayList<FunctionInformationImpl>();
		IContextInformation contextInformation;
		
		sensors.add("Timer");
		FunctionInformationImpl function = new FunctionInformationImpl("Timer",
				"Timer/timer", 0f, 0f, 0f, sensors);
				
		permissions.add("Location");
		functionsList.add(function);

		contextInformation = new ContextInformationImpl("Provides timing", functionsList, "Timer", permissions);

		Intent intent = new Intent();
		intent.setClassName("com.main.core",
				"com.main.contextsection.ContextUpdaterImpl");
		intent.addCategory("android.intent.category.LAUNCHER");
		
		intent.putExtra("subscription", idPaquete);
		intent.putExtra("contextInformation", contextInformation);

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startService(intent);
	}

	@Override
	public Boolean sendData(String idPaquete, IDataContext info) {
		Intent intent = new Intent();
		intent.setClassName("com.main.core",
				"com.main.contextsection.ContextUpdaterImpl");
		intent.addCategory("android.intent.category.LAUNCHER");
		intent.putExtra("idPaquete", idPaquete);
		intent.putExtra("info", info);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intent);

		return true;
	}

}
