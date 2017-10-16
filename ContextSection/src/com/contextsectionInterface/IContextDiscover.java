package com.contextsectionInterface;

public interface IContextDiscover {

	public  Boolean isContextOnRepository(String IContextProvider);
	
	public  Boolean registryApplication(String idApplication, String idContextProvider, String type, String permission, String description);
	
	public Boolean unregistryApplication(String idApplication);
	
	public Boolean canSuscribe(String IApplication, String contextProvider);
	
	//public int onDeleteContextProvider(List<String> applications);
	
	
	
	
}
