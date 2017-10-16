package com.eventSectionInterface;

import java.util.Set;

public interface IApplicationAggregator {
	
	Set<String> getApplicationsForAggregate(String idAggregate);
	
	Set<String> getAggreggatesForApplication(String idApplication);
	
	Boolean addNewAggregatesSubscription(String idAggregate, String idApplication);
	
	Boolean removeAggregateSubscription(String idAggregate);
	
	Boolean addSubscriptionToAggregate(String idAggregate, String idApplication);
	
	Boolean removeSubscriptionToAggregate(String idAggregate, String idApplication);
	
	Boolean removeApplication(String idApplication);
}
