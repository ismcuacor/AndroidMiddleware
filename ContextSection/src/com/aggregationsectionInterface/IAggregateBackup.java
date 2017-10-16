package com.aggregationsectionInterface;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.eventSectionInterface.IEventBackup;

public interface IAggregateBackup {
	/* Get method for attribute Events. */
	Collection<Set<IEventBackup<?>>> getEvents();

	/* Get method for attribute Applications. */
	List<IAggregatePredicate> getPredicates();
	
	/* Method that add an Event to the Aggregate. */
	void addEvent(Set<IEventBackup<?>> event, IAggregatePredicate aggregatePredicate);
}
