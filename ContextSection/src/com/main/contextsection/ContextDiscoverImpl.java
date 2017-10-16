package com.main.contextsection;


import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.application.ApplicationInformationImpl;
import com.application.ApplicationRepository;
import com.contextsectionInterface.IContextDiscover;
import com.eventsection.EventImpl;
import com.main.database.DataBase;

public class ContextDiscoverImpl implements IContextDiscover {

	private static ContextDiscoverImpl instance;

	private ContextRepository ContextRepositoryImpl = DataBase.getContextRepositoryInstance();
	private ApplicationRepository ApplicationRepositoryImpl = DataBase.getApplicationRepositoryInstance();
	private Set<String> applications;

	private ContextDiscoverImpl(){
		applications = new HashSet<String>();
	}
	
	public static ContextDiscoverImpl getInstance(){
		if(instance==null)
			instance=new ContextDiscoverImpl();
		return instance;
	}
	
	/*
	 * Esta funci�n comprueba si un Contexto cuyo nombre se pasa
	 * por par�metros est� instalado en el sistema. Devuelve True si la 
	 * encuentra y False en caso contrario.
	 * */
	@Override
	public Boolean isContextOnRepository(String idContextProvider) {
		Boolean res=false;
		
		//Obtiene la lista de contextProviders. Para ello se realiza una consulta al repositorio por medio de ContextRepositoryImpl
		SortedSet<String> contextProvidersList = ContextRepositoryImpl.getContextProviders();
		
		//Comprueba si el contexto solicitado est� registrado en el repositorio
		if(contextProvidersList.contains(idContextProvider))
			res=true;
		
		return res;
	}

	/*
	 * Este m�todo ejecuta el m�todo 'canSubscribe' para comprobar
	 * si una aplicaci�n puede suscribirse a un contexto y, en caso
	 * afirmativo, registra la aplicaci�n en el m�dulo Application
	 * Repository, introduciendo una copia del objeto relacionado con
	 * su identificador (identificador de aplicaci�n, aplicaci�n), adem�s
	 * de relacionar el identificador de aplicaci�n con el de contexto, de
	 * manera que se mantenga la relaci�n de suscripciones permitidas,
	 * siguiendo las pol�ticas de seguridad implantadas en el sistema.
	 * */
	@Override
	public Boolean registryApplication(String idApplication, String idContextProvider, String type, String permission, String description) {
		Boolean res=false;
		Log.i("CP", idContextProvider);
		//La aplicacion debe pasar el filtro de seguridad
		if(canSuscribe(idApplication, idContextProvider)==true){
			//C�digo para asociar un identificador de aplicaci�n (idApplication) con otro (u otros) id's de contexto (idContextProvider)
			if (isContextOnRepository(idContextProvider)==true){
				Log.i("REGISTRY APP", type+permission+" ");
				//C�digo para insertar la aplicaci�n en el ApplicationRepository
				ApplicationRepositoryImpl.addAllowedApplication(idApplication, type, permission, description);

				res=true;
			}
		}
		return res;
	}

	/*
	 * Este m�todo ejecuta el m�todo 'removeAllowedApplications'
	 * del m�dulo 'Application Repository' para eliminar el registro de
	 * la aplicaci�n en el contexto que se pasa como par�metro de
	 * entrada en el sistema. Si adem�s es el �ltimo registro de relaci�n
	 * aplicaci�n-'Context Provider' en el sistema tambi�n se encarga
	 * de eliminar la copia del objeto relacionado con su identificador
	 * en el par (identificador de aplicaci�n, aplicaci�n), utilizando el
	 * m�todo 'removeApplications'.
	 * */
	@Override
	public Boolean unregistryApplication(String idApplication) {
		
		//Elimina del ApplicationRepositry la referencia a la aplicacion (idApplication)
		ApplicationRepositoryImpl.removeAllowedApplication(idApplication);
		
		//TODO Elimina de la relaci�n aplicacion-contexto (del repo App X) la aplicacion solicitada
		
		return true;
	}

	/*
	 * Este m�todo accede al repositorio de pol�ticas para recuperar el
	 * sistema instalado en el Core, procesa la comparaci�n y devuelve
	 * un atributo de tipo Booleano.*/
	@Override
	public Boolean canSuscribe(String idApplication, String contextProvider) {
		// TODO Completar las pol�ticas de seguridad. recordar eliminar del APP X
		Boolean res=true;
			
		return res;
	}

	/*
	 * Este m�todo se encarga de recuperar la lista de aplicaciones
	 * suscritas al 'Context Provider', de manera que se env�e un
	 * mensaje de aviso a todas las aplicaciones que sea necesario.
	 * Posteriormente se encarga de eliminar todo rastro del 'Context
	 * Provider' dentro de la secci�n, es decir, se encarga de
	 * eliminar la relaci�n aplicaci�n-'Context Provider' utilizando el
	 * m�todo 'removeAllowedApplication' del m�dulo 'Application
	 * Repository'.
	 * */
	/*
	@Override
	public int onDeleteContextProvider(List<String> applications) {
		// TODO Auto-generated method stub
		
		//Eliminamos la referencia al context provider
		
		return 0;
	}
	
	private Map<String, List<String>> getAsociacion(){
		return asociacion;
	}
	*/

}
