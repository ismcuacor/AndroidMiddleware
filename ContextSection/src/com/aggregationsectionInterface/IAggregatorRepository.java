package com.aggregationsectionInterface;

public interface IAggregatorRepository {
	public static final String PROVIDER_NAME = "com.main.contextsection.AggregatorRepository";
	// public static final Uri CONTENT_URI = Uri.parse("content://"
	// + PROVIDER_NAME + "/contextRepository");
	public static final String _ID = "_id";
	public static final String SUBSCRIPTION = "aggregator";

	public <T> IAggregateCore getAggreggate(Integer idAggregate);

	public String addAggreggate(IAggregateCore aggreggate);

	public void removeAggreggate(Integer idAggreggate);

	// public abstract int delete(Uri uri, String selection, String[]
	// selectionArgs);
	//
	// public abstract String getType(Uri uri);
	//
	// public abstract Uri insert(Uri uri, ContentValues values);
	//
	// public abstract boolean onCreate();
	//
	// public abstract Cursor query(Uri uri, String[] projection,
	// String selection, String[] selectionArgs, String sortOrder);
	//
	// public abstract int update(Uri uri, ContentValues values, String
	// selection,
	// String[] selectionArgs);

}
