package com.main.contextsection;

import java.util.SortedSet;

import com.contextsectionInterface.IContextInformationCore;

public interface ContextRepository {

	public static final String PROVIDER_NAME = "com.main.contextsection.ContextRepository";
//	public static final Uri CONTENT_URI = Uri.parse("content://"
//			+ PROVIDER_NAME + "/contextRepository");
	public static final String _ID = "_id";
	public static final String SUBSCRIPTION = "subscription";

	//Metodo observador para saber que context providers se encuentran subscritos al core.
	public abstract SortedSet<String> getContextProviders();

	// Añade un nuevo Context Provider a la subscripcion
	public abstract boolean addAccessStorage(IContextInformationCore context);

	//Avisar al módulo suscrito (Context Listener) en el caso de que se produzca una actualización en los datos que hay almacenados en el módulo.
	public abstract void onChange(Change change, String idContextProvider)
			throws Exception;

	/*Cuando el módulo 'Context Subscription' solicite la
	eliminación de un 'Context Provider' dentro del repositorio eliminará
	este registro y enviará un nuevo aviso de eliminación a través del
	método 'onChange' al módulo 'Context Listener' con el identificador
	del 'Context Provider' asociado.*/
	public abstract void deleteContextProvider(String idContextProvider)
			throws Exception;

//	public abstract int delete(Uri uri, String selection, String[] selectionArgs);
//
//	public abstract String getType(Uri uri);
//
//	public abstract Uri insert(Uri uri, ContentValues values);
//
//	public abstract boolean onCreate();
//
//	public abstract Cursor query(Uri uri, String[] projection,
//			String selection, String[] selectionArgs, String sortOrder);
//
//	public abstract int update(Uri uri, ContentValues values, String selection,
//			String[] selectionArgs);

}