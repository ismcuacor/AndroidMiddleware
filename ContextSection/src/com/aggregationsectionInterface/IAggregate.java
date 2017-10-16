package com.aggregationsectionInterface;

import java.util.Collection;
import java.util.List;

import com.eventsection.EventImpl;

import android.os.Parcelable;

public interface IAggregate extends Parcelable {
	/* Get method for attribute Events. */
	Collection<List<EventImpl>> getEvents();

	/* Get method for attribute Applications. */
	List<String> getPredicates();
	
	/* Method that add an Event to the Aggregate. */
	void addEvent(List<EventImpl> event, String aggregatePredicate);
}
