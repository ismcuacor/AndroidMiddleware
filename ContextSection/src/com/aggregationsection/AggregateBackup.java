package com.aggregationsection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.aggregationsectionInterface.IAggregateBackup;
import com.aggregationsectionInterface.IAggregatePredicate;
import com.eventSectionInterface.IEventBackup;

public class AggregateBackup implements IAggregateBackup {
	HashMap<IAggregatePredicate, Set<IEventBackup<?>>> aggregatePredicates;

	@Override
	public Collection<Set<IEventBackup<?>>> getEvents() {
		return aggregatePredicates.values();
	}

	@Override
	public List<IAggregatePredicate> getPredicates() {
		ArrayList<IAggregatePredicate> predicateList = new ArrayList<IAggregatePredicate>();
		for (IAggregatePredicate aggregatePredicate:aggregatePredicates.keySet()){
			predicateList.add(aggregatePredicate);
		}
		return predicateList;
	}

	@Override
	public void addEvent(Set<IEventBackup<?>> event,
			IAggregatePredicate aggregatePredicate) {
		aggregatePredicates.put(aggregatePredicate, event);
	}

}