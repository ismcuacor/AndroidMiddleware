package com.eventSectionInterface;

import java.util.List;
import java.util.Set;

import com.aggregationsectionInterface.IAggregate;
import com.aggregationsectionInterface.IAggregateCore;
import com.eventsection.EventCoreImpl;

public interface IEventSuscription {
	
	boolean registry(String idApplication, IAggregate aggregate, String type, String[] Permissions);
	
	void onUnregistryApplication(String idApplication);
	
	Boolean applicationCanSuscribeContext(String idApplication, String idContextProvider);
	
	Set<String> registryInEventRepository(String idApplication, List<EventCoreImpl> event);
	
	String registryInAggregatorRepository(IAggregateCore aggregate);
	
	void registryInAppX(String idApp, Integer idAggregator);
	
	void unregistryInAppX(String idApp, Integer idAggregator);
	
	void unregistryInAggregatorRepository(Integer idAggregator);
	
	void onUnregistryContextProvider();
	
	Boolean ping();
}
