package com.eventsection;

import android.util.Log;

import com.contextsectionInterface.IDataContextCore;
import com.datasectionInterface.IDataRepository;
import com.eventSectionInterface.ICorePredicate;
import com.eventSectionInterface.IEvent;
import com.eventSectionInterface.IEventCore;
import com.main.database.DataBase;

public class EventCoreImpl implements IEventCore {

	private String idContext;
	private String idFunction;
	private ICorePredicate corePredicate;
	private Object value;
	CorePredicate corePredicateFactory = new CorePredicate();
		
	//TODO
	//NO ME TERMINA DE GUSTAR ESTO. OBTIENE LA INSTANCIA SIEMPRE. PENSAR DE NUEVO
	private IDataRepository DataRepository = DataBase.getDataRepositoryInstance();
		
	public EventCoreImpl(String idContext, String idFunction,
			ICorePredicate corePredicate, Object value) {
		this.idContext = idContext;
		this.idFunction = idFunction;
		this.corePredicate = corePredicate;
		this.value = value;
	}
	
	public EventCoreImpl(IEvent event){
		this.idContext = event.getIdContext();
		this.idFunction = event.getIdFunction();
		String s = event.getCorePredicate();
		Log.w("",s + corePredicateFactory.toString());
		try{
			this.corePredicate = corePredicateFactory.getCorePredicate(s);
		}catch (RuntimeException e){
			Log.e("Error en Core predicate", e.toString());
		}
		
		this.value = event.getValue();
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
	public Object getValue() {
		return value;
	}
	
//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//
//		dest.writeString(idContext);
//		dest.writeString(idFunction);
//		dest.writeParcelable(corePredicate, 0);
//		//dest.writeParcelable((Parcelable) value, 0);
//	}

//	public void readFromParcel (Parcel in){
//		idContext = in.readString();
//		idFunction = in.readString();
//		corePredicate = in.readString();
//		//value = in.readParcelable(this.getClass().getClassLoader()); 
//	}
//	
//	public static final Parcelable.Creator<EventCoreImpl> CREATOR
//    = new Parcelable.Creator<EventCoreImpl>() {
//        public EventCoreImpl createFromParcel(Parcel in) {
//            return new EventCoreImpl(in);
//        }
// 
//        public EventCoreImpl[] newArray(int size) {
//            return new EventCoreImpl[size];
//        }
//    };

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
		
		Object valor=null;
		try{
			valor = dataContext.getValue();
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
	
	public boolean equals(Object o){
		boolean res = false;
		
		if (o instanceof EventCoreImpl){
			EventCoreImpl event = (EventCoreImpl) o;
			if (this.getIdContext().equals(event.getIdContext()) && 
					this.getIdFunction().equals(event.getIdFunction()) &&
					this.getCorePredicate().equals(event.getCorePredicate()) &&
					this.getValue().equals(event.getValue())){
				res = true;
			}
		}
		return res;
	}
	
	public String toString(){
		Log.w("------------------------------------------------------------------------", this.getIdContext()+","+this.getIdFunction()+","+this.getCorePredicate().toString()+","+this.getValue().hashCode() + "l");
		return this.getIdContext()+","+this.getIdFunction()+","+this.getCorePredicate().toString()+","+this.getValue().hashCode();
	}
}
