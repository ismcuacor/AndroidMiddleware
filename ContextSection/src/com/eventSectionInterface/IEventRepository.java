package com.eventSectionInterface;

import java.util.Map;

import com.eventsection.EventCoreImpl;

import android.net.Uri;

public interface IEventRepository {
	
	public static final String MIME_TYPE_SINGLE = "vnd.android.cursor.item/vnd.eventsection.eventRepository";
	public static final String MIME_TYPE_MULTIPLE = "vnd.android.cursor.dir/vnd.eventsection.eventRepository";
	public static final String PROVIDER_NAME = "com.eventSection.eventRepository";
	public static final String PATH_SINGLE = "eventRepository/#";
	public static final String PATH_MULTIPLE = "eventRepository";
	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/" + PATH_MULTIPLE);
	public static final String DB_NAME = "eventRepository.db";
	//TODO definir con cuidado estas constantes
	public static final String DB_TABLE = "contextData";
	public static final int DB_VERSION = 1;
	
	
	String nextIdEvent();
	
	EventCoreImpl getEvent(String idEvent);
	
	String addEvent(EventCoreImpl event);
	
	EventCoreImpl removeEvent(String IdEvent);
	
	Map<String, EventCoreImpl> getEventsforData(Integer idContextProvider, String function);
}
