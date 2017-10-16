package com.eventSectionInterface;

import java.util.Set;

public interface IAppX {
	Boolean isApplicationAllowedToContext(String idApplication, String idContext);
	
	Set<String> getApplicationsForAggregate(Integer idAggregate);
	
	Set<Integer> getAggreggatesForApplication(String idApplication);
	
	Boolean addNewAggregatesSubscription(Integer idAggregate, String idApplication);
	
	Boolean removeAggregateSubscription(Integer idAggregate);
	
	Boolean addSubscriptionToAggregate(Integer idAggregate, String idApplication);
	
	Boolean removeSubscriptionToAggregate(Integer idAggregate, String idApplication);
	
	Boolean removeApplication(String idApplication);
}
