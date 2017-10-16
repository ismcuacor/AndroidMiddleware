package com.application;

import com.aggregationsectionInterface.IAggregate;

public interface IApplication {
	
	Boolean subscribeToContext(String idContext);
	String subscribeToAggregate(IAggregate aggregate);
	String unSubscribeToAggregate(IAggregate aggregate);
	void onAggregateActiveEvent(String idAggregate);
	void onAggregateDefuseEvent(String idAggregate);

}
