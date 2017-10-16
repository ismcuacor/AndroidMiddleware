package com.aggregationsection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.eventsection.EventImpl;
import com.aggregationsectionInterface.IAggregate;

import android.os.Parcel;
import android.os.Parcelable;

public class Aggregate implements IAggregate, Parcelable {
	HashMap<String, List<EventImpl>> aggregatePredicates;
	
	public Aggregate(HashMap<String, List<EventImpl>> aggregatePredicates){
		this.aggregatePredicates = aggregatePredicates;
	}
	
	private Aggregate (Parcel p){
		aggregatePredicates = new HashMap<String, List<EventImpl>>();
		int N = p.readInt();
		this.readFromParcel(p, N);
	}
	
	@Override
	public Collection<List<EventImpl>> getEvents() {
	return aggregatePredicates.values();
	}

	@Override
	public List<String> getPredicates() {
		ArrayList<String> predicateList = new ArrayList<String>();
		for (String aggregatePredicate:aggregatePredicates.keySet()){
			predicateList.add(aggregatePredicate);
		}
		return predicateList;
	}

	public void addEvent(List<EventImpl> event, String aggregatePredicate) {
		aggregatePredicates.put(aggregatePredicate, event);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Set <String> predicates = aggregatePredicates.keySet();
		Integer i = aggregatePredicates.size();
		
		dest.writeInt(i);
		
		Iterator<String> it = predicates.iterator();
		while (it.hasNext()){
			String key = it.next();
			dest.writeString(key);
			dest.writeTypedList(aggregatePredicates.get(key));
		}
	}
	
	public void readFromParcel (Parcel in, Integer N){

		String keyAux;
		List<EventImpl> eventAux; 
		
		for (int j = 0; j < N; j++){
			keyAux = new String();
			keyAux = in.readString();
			eventAux = new LinkedList<EventImpl> ();
			in.readTypedList(eventAux, EventImpl.CREATOR);
			aggregatePredicates.put(keyAux, eventAux);
		} 
	}
	
	public static final Parcelable.Creator<Aggregate> CREATOR
    = new Parcelable.Creator<Aggregate>() {
        public Aggregate createFromParcel(Parcel in) {
            return new Aggregate(in);
        }
 
        public Aggregate[] newArray(int size) {
            return new Aggregate[size];
        }
    };
}