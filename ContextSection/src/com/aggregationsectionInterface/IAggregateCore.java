package com.aggregationsectionInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.contextsectionInterface.IDataContextCore;
import com.eventsection.EventCoreImpl;

public interface IAggregateCore {
	/* Get method for attribute Events. */
	Collection<List<EventCoreImpl>> getEvents();

	/* Method that evaluate the current state of the aggregate. */
	boolean evaluate();

	/* Method that evaluate a set of Events according to their values. */
	boolean[] evaluatePredicates(Map<IAggregatePredicate, List<EventCoreImpl>> values);

	/* Method that evaluate a predicate according to an array of values. */
	boolean evaluatePredicate(IAggregatePredicate aggregatePredicate,
			List<EventCoreImpl> events);

	/* Method that add an Event to the Aggregate. */
	void addEvent(List<EventCoreImpl> event, IAggregatePredicate aggregatePredicate);

	/* Get method for attribute Applications. */
	Set<IAggregatePredicate> getPredicates();

	/*
	 * Method that evaluate a the Aggregate checking if is affected by the
	 * change of an specific data.
	 */
	boolean isAggregateAffected(IDataContextCore data);

	HashMap<IAggregatePredicate, List<EventCoreImpl>> getAggregatePredicates();

}
