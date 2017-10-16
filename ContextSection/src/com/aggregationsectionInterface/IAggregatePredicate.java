package com.aggregationsectionInterface;

public interface IAggregatePredicate {
	/* Evaluate value of the predicate */
	Boolean apply(Boolean[] values);
}
