package com.eventsection;

import java.util.HashSet;
import java.util.Set;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.eventSectionInterface.IAppX;
import com.eventSectionInterface.IEventProvider;
import com.main.database.DataBase;

public class EventProvider extends Service implements IEventProvider {

	private IAppX AggregateApplicationRepository = DataBase.getAggregateApplicationInstance();
	Intent intent;
	Bundle bundle;
	String mainClass;
	
	private final IBinder mBinder = new Binder();
	private static EventProvider instance;
	private static Integer instances = 0;

	//TODO Ver como hacer para no hacer el constructor público
	
	public EventProvider(){
	}
	
	public static EventProvider getInstance(){
		if (instances == 0){
			instance = new EventProvider();
			instances++;
		}
		return instance;
	}
	
	@Override
	public void onNewAggregationActivation(Integer idAggregation) {
		//Consulta la AppX con el idAggregator para obtener un conjunto de identificadores de aplicación
		Set<String> applicationsID;
		
		applicationsID = AggregateApplicationRepository.getApplicationsForAggregate(idAggregation);
		
		//Recorre la lista de idApplication y obtiene la lista de IApplication
		//El tipo SendingThread está definido al final de este documento
		if(applicationsID != null){
			for (String e: applicationsID){
				SendingThread thread = new SendingThread(this, e, idAggregation, true);
				thread.start();
			}
		}
		else
		{
			Log.w("NewAggregationActivation", "No apps waiting for it");
		}
	}
	
	@Override
	public void sendActivationMessageToApplication(String application, Integer idAgregation){
	
		//TODO: Enviar la acción a la aplicacion correspondiente mediante intents
		intent = new Intent();
		bundle = new Bundle();
		
		intent.putExtras(bundle);
		
		String mainClass = application + "main";
		intent.setClassName(application, mainClass);
		intent.addCategory("android.intent.category.LAUNCHER");
		
		startActivity(intent);

	}

	@Override
	public void onNewDefuseAggregation(Integer idAggregation) {
		//Consulta la AppX con el idAggregator para obtener un conjunto de identificadores de aplicación
		Set<String> applicationsID = AggregateApplicationRepository.getApplicationsForAggregate(idAggregation);
		
		//Recorre la lista de idApplication y obtiene la lista de IApplication
		for (String e: applicationsID){
			SendingThread thread = new SendingThread(this, e, idAggregation, false);
			thread.start();
		}
	
	}
	
	@Override
	public void sendDefuseMessageToApplication(String application, Integer idAggregator){
		
		//TODO: Enviar la acción a la aplicacion correspondiente mediante intents
		intent = new Intent();
		bundle = new Bundle();
		mainClass = application + "main";
		intent.setClassName(application, mainClass);
		intent.putExtras(bundle);			
		intent.addCategory("android.intent.category.LAUNCHER");
		
		startActivity(intent);

	}

	private class SendingThread extends Thread{
		private EventProvider eventProvider;
		private String idApplication;
		private Integer idAggregation;
		private boolean activate;
		
		public SendingThread(EventProvider eventProvider, String idApplication, 
				Integer idAggregation, boolean activate){
			this.eventProvider = eventProvider;
			this.idApplication = idApplication;
			this.idAggregation = idAggregation;
			this.activate = activate;
		}
		
		@Override
		public void run() {
			//En funcion de activate, se envia a la aplicación un mensaje de activación o descativación
			if (activate == true){
				eventProvider.sendActivationMessageToApplication(idApplication, idAggregation);
				Log.w("Activation", "yeah");
			}else{
				Log.w("Llegué", "yeah");
				eventProvider.sendDefuseMessageToApplication(idApplication, idAggregation);
				
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
	    Log.w("Llegué", "yeah");
	    return mBinder;
	}
}
