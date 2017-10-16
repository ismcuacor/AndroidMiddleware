package com.eventsection;

import com.eventSectionInterface.IEvent;

import android.os.Parcel;
import android.os.Parcelable;

public class EventImpl implements IEvent {

	private String idContext;
	private String idFunction;
	private String corePredicate;
	private Object value;

	public EventImpl(String idContext, String idFunction,
			String corePredicate, Object value) {
		this.idContext = idContext;
		this.idFunction = idFunction;
		this.corePredicate = corePredicate;
		this.value = value;
	}
	
	public EventImpl(Parcel in){
		readFromParcel(in);
	}

	@Override
	public String getIdContext() {
		return idContext;
	}

	@Override
	public String getIdFunction() {
		return idFunction;
	}

	@Override
	public String getCorePredicate() {
		return corePredicate;
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(idContext);
		dest.writeString(idFunction);
		dest.writeString(corePredicate);
		dest.writeParcelable((Parcelable) value, 0);
	}

	public void readFromParcel (Parcel in){
		idContext = in.readString();
		idFunction = in.readString();
		corePredicate = in.readString();
		value = in.readParcelable(this.getClass().getClassLoader()); 
	}
	
	public static final Parcelable.Creator<EventImpl> CREATOR
    = new Parcelable.Creator<EventImpl>() {
        public EventImpl createFromParcel(Parcel in) {
            return new EventImpl(in);
        }
 
        public EventImpl[] newArray(int size) {
            return new EventImpl[size];
        }
    };

	public boolean equals(Object o){
		boolean res = false;
		
		if (o instanceof EventImpl){
			EventImpl event = (EventImpl) o;
			if (this.getIdContext().equals(event.getIdContext()) && 
					this.getIdFunction().equals(event.getIdFunction()) &&
					this.getCorePredicate().equals(event.getCorePredicate()) &&
					this.getValue().equals(event.getValue())){
				res = true;
			}
		}
		return res;
	}
}
