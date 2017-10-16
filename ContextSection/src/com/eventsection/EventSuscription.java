package com.eventsection;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.aggregationsection.AggregateCore;
import com.aggregationsectionInterface.IAggregate;
import com.aggregationsectionInterface.IAggregateCore;
import com.aggregationsectionInterface.IAggregatorRepository;
import com.application.ApplicationInformationImpl;
import com.application.ApplicationRepository;
import com.eventSectionInterface.IAppX;
import com.eventSectionInterface.IEventSuscription;
import com.eventSectionInterface.IEventRepository;
import com.main.contextsection.ContextDiscoverImpl;
import com.main.database.DataBase;

public class EventSuscription extends Service implements IEventSuscription {
	private ApplicationRepository ApplicationRepositoryImpl = DataBase.getApplicationRepositoryInstance();
	private IAggregatorRepository AggregatorRepository = DataBase.getAggregatorRepositoryInstance();
	private IAppX AggregateApplicationRepository = DataBase.getAggregateApplicationInstance();
	private IEventRepository EventRepositoryImpl = DataBase.getEventRepositoryInstance();
	private int idAggregateCore = 0;
	private ContextDiscoverImpl aux = ContextDiscoverImpl.getInstance();
	private Set<String> applications = new HashSet<String>();

	@Override
	public boolean registry(String idApplication, IAggregate aggregate, String type, String[] Permissions) {
		boolean res = true;
		
//		ContextDiscoverImpl.getInstance();
		
		List<List<EventCoreImpl>> eventsCollection = new LinkedList<List<EventCoreImpl>>();
		List<EventCoreImpl> eventsSetAux;
		IAggregateCore aggregateCore = null;
		EventCoreImpl eventCore = null;

		Set<String> idEvents = new HashSet<String>();
		Integer i = 0;
		
		for (List<EventImpl> eventList:aggregate.getEvents()){
			eventsSetAux = new LinkedList<EventCoreImpl>();
			for (EventImpl event:eventList){
				eventCore = new EventCoreImpl(event);
				eventsSetAux.add(eventCore);
			}
			eventsCollection.add(eventsSetAux);
			idEvents = this.registryInEventRepository(idApplication, eventsSetAux);

			i++;
		}
		
		aggregateCore = new AggregateCore(aggregate.getPredicates(), eventsCollection);
		
		this.registryInAggregatorRepository(aggregateCore);
		this.registryInAppX(idApplication, idAggregateCore);
		idAggregateCore++;
		return res;
	}

	@Override
	public void onUnregistryApplication(String idApplication) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean applicationCanSuscribeContext(String idApplication, String idContextProvider) {
		// TODO Aplicar políticas de seguridad
		//Boolean res = ApplicationRepository.isApplicationAllowed(idApplication);
		return true;
	}

	
	@Override
	public Set<String> registryInEventRepository(String idApplication, List<EventCoreImpl> events){
		Set<String> idEventos = new HashSet<String>();
		for (EventCoreImpl event:events){
			
			//Check the allowance to registry this Context
			if(!applicationCanSuscribeContext(idApplication, event.getIdContext())){
				throw new RuntimeException("Application Cannot subscribe Context");
			}
			EventRepositoryImpl.addEvent(event);
		}
		
		/*Inserta todos los eventos en el EventRepository
		 * y añade el String que devuelva al Set<String>
		 */
		for (EventCoreImpl event: events){
			String idEvento = EventRepositoryImpl.addEvent(event);
			idEventos.add(idEvento);
		}
		return idEventos;
	}

//	@Override
//	public String registryInAggregatorRepository(IAggregateCore aggregate) {
//		
//		String idAggregator = AggregatorRepository.addAggreggate(aggregate);
//		return idAggregator;
//	}
	
	@Override
	public String registryInAggregatorRepository(IAggregateCore aggregate) {
		
		String idAggregator = AggregatorRepository.addAggreggate(aggregate);
		return idAggregator;
	}

	@Override
	public void registryInAppX(String idApp, Integer idAggregator) {
		//ApplicationRepository.addAllowedApplication(idApp, "", "", "");
		AggregateApplicationRepository.addNewAggregatesSubscription(idAggregator, idApp);
	}

	@Override
	public void unregistryInAppX(String idApp, Integer idAggregator) {
		AggregateApplicationRepository.removeApplication(idApp);
	}

	@Override
	public void unregistryInAggregatorRepository(Integer idAggregator) {
		AggregatorRepository.removeAggreggate(idAggregator);
	}

	@Override
	public void onUnregistryContextProvider() {
		// TODO Auto-generated method stub
	}

	@Override
	public Boolean ping() {
		Boolean res = false;
		//TODO de momento se queda sin implementar
		return res;
	}

	@Override
	public IBinder onBind(Intent intent) {
		//TODO auto-generated method stub
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		ApplicationInformationImpl application = intent.getParcelableExtra("info");
		String idApplication = intent.getStringExtra("idPaquete");
		if (application != null && idApplication != null) {
				if(!applications.contains(idApplication)){
					for(List<EventImpl> eventList:application.getAggregate().getEvents()){
						for (EventImpl event:eventList){
							aux.registryApplication(idApplication, event.getIdContext(), application.getType(), application.getPermission(), "AUX");
						}
					}
					applications = ApplicationRepositoryImpl.getApplications();
				}
				String[] s = new String[1];
				s[0] = "Location";
				registry(idApplication, application.getAggregate(), application.getType(), s);
		}
		else{
			Log.w("Event Subscription", "Datos vacíos");
		}
		
		return START_NOT_STICKY;
	}
}
