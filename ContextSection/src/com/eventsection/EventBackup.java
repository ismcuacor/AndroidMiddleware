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
		 * Recuperar el último dato asociado al 'Context 
		 * Provider' y función determinado en los atributos 'idContext' 
		 * e 'idFunction'. Para ello, realiza una consulta al módulo 
		 * 'Data Repository' a través de su método 
		 * 'getAccessStorage'.
		*/
		IDataContextCore dataContext = DataRepository.getAccessStorage(idContext, idFunction);
		
		/*
		 * Una vez ha obtenido el dato, es necesario recuperar 
		 * el valor asociado al mismo y realizar un casting al tipo de 
		 * objeto 'T' específico. En caso de que se produzca algún 
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
		 * El siguiente paso consiste en la evaluación del 
		 * predicado utilizando el objeto 'ICorePredicate' almacenado 
		 * en el objeto 'IEvent' como atributo. Como el conjunto de 
		 * objetos 'ICorePredicate' son predefinidos para el sistema a 
		 * través de librerías, no existe riesgo de seguridad en la 
		 * función de evaluación de los valores. Para realizar esta 
		 * evaluación se utiliza el método 'apply' del atributo 
		 * 'ICorePredicate', al que se le pasan como parámetros de 
		 * entrada el valor obtenido en la evaluación del dato del paso 
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
