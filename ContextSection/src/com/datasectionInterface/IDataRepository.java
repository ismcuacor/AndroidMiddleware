package com.datasectionInterface;

import java.util.List;

import android.content.ContentValues;
import android.net.Uri;

import com.contextsectionInterface.IDataContext;
import com.contextsectionInterface.IDataContextCore;

public interface IDataRepository {

	public static final String MIME_TYPE_SINGLE = "vnd.android.cursor.item/vnd.datasection.dataRepository";
	public static final String MIME_TYPE_MULTIPLE = "vnd.android.cursor.dir/vnd.datasection.dataRepository";
	public static final String PROVIDER_NAME = "com.datasection.dataRepository";
	public static final String PATH_SINGLE = "dataRepository/#";
	public static final String PATH_MULTIPLE = "dataRepository";
	// public static final Uri CONTENT_URI = Uri.parse("content://"
	// + PROVIDER_NAME + "/" + PATH_MULTIPLE);
	public static final String DB_NAME = "dataRepository.db";
	public static final String DB_TABLE = "Data_";
	public static final int DB_VERSION = 2;

	public abstract IDataContextCore getAccessStorage(String idContextProvider,
			String idFunction);

	public abstract List<IDataContextCore> getAccessStorage(long timestampInit,
			long timestampEnd);

	public abstract void addAccessStorage(IDataContextCore data);

	public abstract void onChange(IDataContext dataContext);

	public abstract void garbageCollector();

	// public abstract int delete(Uri uri, String selection, String[]
	// selectionArgs);
	//
	// public abstract String getType(Uri uri);
	//
	// public abstract Uri insert(Uri uri, ContentValues contentValues);
	//
	// public abstract boolean onCreate();
	//
	// public abstract Cursor query(Uri uri, String[] projection,
	// String selection, String[] selectionArgs, String sortOrder);
	//
	// public abstract int update(Uri uri, ContentValues values, String
	// selection,
	// String[] selectionArgs);

	// Métodos que encapsulan a insert, update y delete. USAR PREFERENTEMENTE
	public abstract Uri insertDataRepository(Uri uri,
			ContentValues contentValues, IDataContext dataContext);

	public abstract int updateDataRepository(Uri uri, ContentValues values,
			String selection, String[] selectionArgs, IDataContext dataContext);

	public abstract int deleteDataRepository(Uri uri, String selection,
			String[] selectionArgs, IDataContext dataContext);
}