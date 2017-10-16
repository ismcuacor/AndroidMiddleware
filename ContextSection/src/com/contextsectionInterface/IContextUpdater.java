package com.contextsectionInterface;

import java.util.SortedSet;


public interface IContextUpdater {

	public abstract void onContextChange(SortedSet<String> cp);

	public abstract boolean isContextAccepted(String idBundle);

	public abstract boolean updateContextData(IDataContextCore data);

	public abstract String dataToXML(IDataContextCore data);

}