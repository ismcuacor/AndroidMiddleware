package com.aggregationsection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aggregationsectionInterface.IAggregateCore;
import com.aggregationsectionInterface.IAggregatePredicate;
import com.contextsectionInterface.IDataContextCore;
import com.eventsection.EventCoreImpl;
import com.eventSectionInterface.IEventRepository;
import com.main.database.DataBase;

public class AggregateCore implements IAggregateCore {

	private HashMap<IAggregatePredicate, List<EventCoreImpl>> aggregatePredicates;
	private IEventRepository EventRepositoryImpl;
	
	public AggregateCore() {
		EventRepositoryImpl = DataBase.getEventRepositoryInstance();
		aggregatePredicates = new HashMap<IAggregatePredicate, List<EventCoreImpl>>();
	}
	
	public AggregateCore(List<String> predicates, List<List<EventCoreImpl>> events) {
		EventRepositoryImpl = DataBase.getEventRepositoryInstance();
		aggregatePredicates = new HashMap<IAggregatePredicate, List<EventCoreImpl>>();
		Integer i = 0;
		AggregatePredicate aggregatePredicateFactory = new AggregatePredicate();
		IAggregatePredicate aggregatePredicate;
		for (String s:predicates){
			aggregatePredicate = aggregatePredicateFactory.getAggregatePredicate(s);
			aggregatePredicates.put(aggregatePredicate, events.get(i));
			i++;
		}
	}
		
	@Override
	public List<List<EventCoreImpl>> getEvents() {
		List<List<EventCoreImpl>> eventCollection = new LinkedList<List<EventCoreImpl>>();
		List<EventCoreImpl> events;
		
		for(List<EventCoreImpl> idEvents:aggregatePredicates.values()) {
			events = new LinkedList<EventCoreImpl>();
			for(EventCoreImpl idEvent:idEvents) {
				events.add(EventRepositoryImpl.getEvent(idEvent.toString()));
			}
			eventCollection.add(events);
		}
		return eventCollection;
	}

	@Override
	public Set<IAggregatePredicate> getPredicates() {
		return aggregatePredicates.keySet();
	}
	
	@Override
	public HashMap<IAggregatePredicate, List<EventCoreImpl>> getAggregatePredicates(){
		return aggregatePredicates;
	}

	@Override
	public void addEvent(List<EventCoreImpl> events,
			IAggregatePredicate aggregatePredicate) {
		aggregatePredicates.put(aggregatePredicate, events);
	}

	@Override
	public boolean isAggregateAffected(IDataContextCore data) {
		String idContext = data.getIdContextProvider();
		String idFunction = data.getIdFunction();
		
		for (List<EventCoreImpl> events : getEvents()) {
			for (EventCoreImpl event : events) {
				if (event.getIdContext().equals(idContext)
						&& event.getIdFunction().equals(idFunction)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean evaluate() {
		Integer i = 0;
		boolean result = true;
				
		for (IAggregatePredicate aggregatePredicate : aggregatePredicates.keySet()) {
		
			result = result
						& evaluatePredicate(aggregatePredicate,
								getEventsToPredicate(aggregatePredicate));
				i++;
		}
		return result;
	}
	
	private List<EventCoreImpl> getEventsToPredicate(IAggregatePredicate aggregatePredicate) {
		List<EventCoreImpl> events = new LinkedList<EventCoreImpl>();
		
		for(EventCoreImpl idEvent:aggregatePredicates.get(aggregatePredicate)) {
			events.add(EventRepositoryImpl.getEvent(idEvent.toString()));
		}
		return events;
	}

	@Override
	public boolean[] evaluatePredicates(Map<IAggregatePredicate, List<EventCoreImpl>> events) {
		Integer i = 0;
		boolean[] evaluatedValues = new boolean[events.keySet().size()];

		for (IAggregatePredicate aggregatePredicate : events.keySet()) {
			evaluatedValues[i] = evaluatePredicate(aggregatePredicate, events.get(aggregatePredicate));
			i++;
		}
		return evaluatedValues;
	}

	@Override
	public boolean evaluatePredicate(IAggregatePredicate aggregatePredicate,
			List<EventCoreImpl> events) {
		Integer i = 0;
		Boolean[] evaluatedValues = new Boolean[events.size()];

		for (EventCoreImpl event : events) {
			evaluatedValues[i] = event.evaluateEvent();
			i++;
		}

		return aggregatePredicate.apply(evaluatedValues);
	}

}
