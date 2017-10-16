package com.eventSectionInterface;

public interface IEventProvider {
	
	void onNewAggregationActivation(Integer idAggregation);
	
	void sendActivationMessageToApplication(String idApplication, Integer idAggregation);
	
	void onNewDefuseAggregation(Integer idAggregation);
	
	void sendDefuseMessageToApplication(String idApplication, Integer idAggregator);
}
