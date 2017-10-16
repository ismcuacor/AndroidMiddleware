package com.aggregationsection;

import com.aggregationsectionInterface.IAggregatePredicate;

public class AggregatePredicateAddition implements IAggregatePredicate {

	@Override
	public Boolean apply(Boolean[] values) {
		Boolean result = true;
		
		for(boolean value:values)
		{
			result = result & value;
		}

		return result;
	}

}
