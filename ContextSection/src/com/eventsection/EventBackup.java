package com.eventsection;

import com.contextsectionInterface.IDataContextCore;
import com.eventSectionInterface.ICorePredicate;
import com.eventSectionInterface.IEventBackup;
import com.main.database.DataBase;
import com.datasectionInterface.IDataRepository;

public class EventBackup<T> implements IEventBackup<T> {

	private String idContext;
	private String idFunction;
	private ICorePredicate corePredicate;
	private T value;
	
	//TODO
	//NO ME TERMINA DE GUSTAR ESTO. OBTIENE LA INSTANCIA SIEMPRE. PENSAR DE NUEVO
	private IDataRepository DataRepository = DataBase.getDataRepositoryInstance();
	
	public EventBackup(String idContext, String idFunction,
			ICorePredicate corePredicate, T value) {
		this.idContext = idContext;
		this.idFunction = idFunction;
		this.corePredicate = corePredicate;
		this.value = value;
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
	public ICorePredicate getCorePredicate() {
		return corePredicate;
	}

	@Override
	public T getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean evaluateEvent() {
		/*
		 * Recuperar el �ltimo dato asociado al 'Context 
		 * Provider' y funci�n determinado en los atributos 'idContext' 
		 * e 'idFunction'. Para ello, realiza una consulta al m�dulo 
		 * 'Data Repository' a trav�s de su m�todo 
		 * 'getAccessStorage'.
		*/
		IDataContextCore dataContext = DataRepository.getAccessStorage(idContext, idFunction);
		
		/*
		 * Una vez ha obtenido el dato, es necesario recuperar 
		 * el valor asociado al mismo y realizar un casting al tipo de 
		 * objeto 'T' espec�fico. En caso de que se produzca alg�n 
		 * error en este paso (por ejemplo, no se puede realizar el 
		 * casting) se comunica al usuario mediante excepciones.
		 */
		
		T valor=null;
		try{
			valor = (T) dataContext.getValue();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		/*
		 * El siguiente paso consiste en la evaluaci�n del 
		 * predicado utilizando el objeto 'ICorePredicate' almacenado 
		 * en el objeto 'IEvent' como atributo. Como el conjunto de 
		 * objetos 'ICorePredicate' son predefinidos para el sistema a 
		 * trav�s de librer�as, no existe riesgo de seguridad en la 
		 * funci�n de evaluaci�n de los valores. Para realizar esta 
		 * evaluaci�n se utiliza el m�todo 'apply' del atributo 
		 * 'ICorePredicate', al que se le pasan como par�metros de 
		 * entrada el valor obtenido en la evaluaci�n del dato del paso 
		 * anterior y el valor almacenado en el atributo 'Value'.
		 * return null;
		*/
		
		Boolean result = corePredicate.apply(valor, dataContext);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object o){
		boolean res = false;
		
		if (o instanceof EventBackup){
			EventBackup<T> event = (EventBackup<T>) o;
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
