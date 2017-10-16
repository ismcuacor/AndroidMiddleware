package com.main.contextsection;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;

import com.contextsectionInterface.IContextListener;

public class ContextListenerImpl implements IContextListener {
	private static IContextListener instance;
	private Set<Class<?>> observers;
	
	private ContextListenerImpl(){
		observers = new LinkedHashSet<Class<?>>();
	}
	
	public static IContextListener getInstance(){
		if(instance == null) {
			instance = new ContextListenerImpl();
		}
		return instance;
	}
	// Devuelve los observadores subscritos al listener.
	/* (non-Javadoc)
	 * @see com.contextsection.IContextListener#getObservers()
	 */
	@Override
	public Set<Class<?>> getObservers(){
		return observers;
	}
	
	// Este metodo sera llamado por el context repository cada vez que un cp sea actualizado.
	// Se encarga de garantizar integridad entre el context repository y los listeners subscritos.
	/* (non-Javadoc)
	 * @see com.contextsection.IContextListener#onContextRepositoryUpdate(java.util.SortedSet)
	 */
	@Override
	public void onContextRepositoryUpdate(SortedSet<String> cp){
		ContextUpdaterImpl.getInstance().onContextChange(cp);
		/*for(Class<?> c : observers)
		{
			Object obj = c.newInstance();
			Method method = c.getDeclaredMethod("onContextChange");
			method.invoke(obj,cp);
		}*/
		
	}
	// Subscribe un nuevo listener.
	/* (non-Javadoc)
	 * @see com.contextsection.IContextListener#subscribe(java.lang.Class)
	 */
	@Override
	public boolean subscribe (Class<?> observer){
		boolean res = observers.add(observer);
		return res;
	}
	
}
