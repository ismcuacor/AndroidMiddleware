package com.contextsectionInterface;

import java.util.Set;
import java.util.SortedSet;

public interface IContextListener {

	// Devuelve los observadores subscritos al listener.
	public abstract Set<Class<?>> getObservers();

	// Este metodo sera llamado por el context repository cada vez que un cp sea actualizado.
	// Se encarga de garantizar integridad entre el context repository y los listeners subscritos.
	public abstract void onContextRepositoryUpdate(SortedSet<String> cp);

	// Subscribe un nuevo listener.
	public abstract boolean subscribe(Class<?> observer);

}