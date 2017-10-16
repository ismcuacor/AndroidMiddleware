package com.aggregationsection;

import com.aggregationsectionInterface.IAggregatePredicate;

public class AggregatePredicate {

	public IAggregatePredicate getAggregatePredicate(String aggregatePredicateString) {
		if(aggregatePredicateString.equals("PredicateAddition")){
			return new AggregatePredicateAddition();
		}else{
			throw new RuntimeException("Predicate not found");
		}
	}

}
