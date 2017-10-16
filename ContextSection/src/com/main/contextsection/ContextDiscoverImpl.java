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
	 * Esta función comprueba si un Contexto cuyo nombre se pasa
	 * por parámetros está instalado en el sistema. Devuelve True si la 
	 * encuentra y False en caso contrario.
	 * */
	@Override
	public Boolean isContextOnRepository(String idContextProvider) {
		Boolean res=false;
		
		//Obtiene la lista de contextProviders. Para ello se realiza una consulta al repositorio por medio de ContextRepositoryImpl
		SortedSet<String> contextProvidersList = ContextRepositoryImpl.getContextProviders();
		
		//Comprueba si el contexto solicitado está registrado en el repositorio
		if(contextProvidersList.contains(idContextProvider))
			res=true;
		
		return res;
	}

	/*
	 * Este método ejecuta el método 'canSubscribe' para comprobar
	 * si una aplicación puede suscribirse a un contexto y, en caso
	 * afirmativo, registra la aplicación en el módulo Application
	 * Repository, introduciendo una copia del objeto relacionado con
	 * su identificador (identificador de aplicación, aplicación), además
	 * de relacionar el identificador de aplicación con el de contexto, de
	 * manera que se mantenga la relación de suscripciones permitidas,
	 * siguiendo las políticas de seguridad implantadas en el sistema.
	 * */
	@Override
	public Boolean registryApplication(String idApplication, String idContextProvider, String type, String permission, String description) {
		Boolean res=false;
		Log.i("CP", idContextProvider);
		//La aplicacion debe pasar el filtro de seguridad
		if(canSuscribe(idApplication, idContextProvider)==true){
			//Código para asociar un identificador de aplicación (idApplication) con otro (u otros) id's de contexto (idContextProvider)
			if (isContextOnRepository(idContextProvider)==true){
				Log.i("REGISTRY APP", type+permission+" ");
				//Código para insertar la aplicación en el ApplicationRepository
				ApplicationRepositoryImpl.addAllowedApplication(idApplication, type, permission, description);

				res=true;
			}
		}
		return res;
	}

	/*
	 * Este método ejecuta el método 'removeAllowedApplications'
	 * del módulo 'Application Repository' para eliminar el registro de
	 * la aplicación en el contexto que se pasa como parámetro de
	 * entrada en el sistema. Si además es el último registro de relación
	 * aplicación-'Context Provider' en el sistema también se encarga
	 * de eliminar la copia del objeto relacionado con su identificador
	 * en el par (identificador de aplicación, aplicación), utilizando el
	 * método 'removeApplications'.
	 * */
	@Override
	public Boolean unregistryApplication(String idApplication) {
		
		//Elimina del ApplicationRepositry la referencia a la aplicacion (idApplication)
		ApplicationRepositoryImpl.removeAllowedApplication(idApplication);
		
		//TODO Elimina de la relación aplicacion-contexto (del repo App X) la aplicacion solicitada
		
		return true;
	}

	/*
	 * Este método accede al repositorio de políticas para recuperar el
	 * sistema instalado en el Core, procesa la comparación y devuelve
	 * un atributo de tipo Booleano.*/
	@Override
	public Boolean canSuscribe(String idApplication, String contextProvider) {
		// TODO Completar las políticas de seguridad. recordar eliminar del APP X
		Boolean res=true;
			
		return res;
	}

	/*
	 * Este método se encarga de recuperar la lista de aplicaciones
	 * suscritas al 'Context Provider', de manera que se envíe un
	 * mensaje de aviso a todas las aplicaciones que sea necesario.
	 * Posteriormente se encarga de eliminar todo rastro del 'Context
	 * Provider' dentro de la sección, es decir, se encarga de
	 * eliminar la relación aplicación-'Context Provider' utilizando el
	 * método 'removeAllowedApplication' del módulo 'Application
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
