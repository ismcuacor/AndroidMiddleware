package com.main.database;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.aggregationsectionInterface.IAggregateCore;
import com.aggregationsectionInterface.IAggregatePredicate;
import com.aggregationsectionInterface.IAggregatorRepository;
import com.application.ApplicationRepository;
import com.contextsectionInterface.IContextInformationCore;
import com.contextsectionInterface.IContextListener;
import com.contextsectionInterface.IDataContext;
import com.contextsectionInterface.IDataContextCore;
import com.datasectionInterface.IDataRepository;
import com.eventSectionInterface.IAppX;
import com.eventSectionInterface.IEventRepository;
import com.main.contextsection.Change;
import com.main.contextsection.ContextListenerImpl;
import com.main.contextsection.ContextRepository;
import com.main.contextsection.DataContextCoreImpl;
import com.main.contextsection.FunctionInformationImpl;
import com.main.datasection.DataListenerImpl;
import com.eventsection.EventCoreImpl;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

public class DataBase extends ContentProvider {
	public static final String PROVIDER_NAME = "com.main.contextsection.Repository";
	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/repository");

	public static DataBase repository;
	public static String DATABASE_TABLE = "";
	public static String FIELD = "";
	public static String ID = "";

	// Se coloca una variable del mismo tipo que la clase llamada, por
	// convención "instance".
	// Aqui reside todo el secreto del patron SINGLETON ya que dicha variable es
	// la que se instancia
	// por unica vez y se devuelve al cliente.
	Change change = null;
	private static final int REPOSITORY = 1;
	private static final int REPOSITORY_ID = 2;

	protected static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "repository", REPOSITORY);
		uriMatcher.addURI(PROVIDER_NAME, "repository/#", REPOSITORY_ID);
	}

	protected static DatabaseHelper dbHelper;
	private static SQLiteDatabase repositoryDB;

	private static final String DATABASE_NAME = "Repository.db";
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_CREATE1 = "CREATE TABLE LogicalFunction (logicalFunction VARCHAR BINARY NOT NULL, PRIMARY KEY(logicalFunction) );  ";
	private static final String DATABASE_CREATE2 = "CREATE TABLE CorePredicate"
			+ " (corePredicate VARCHAR,   PRIMARY KEY(corePredicate) );  ";
	private static final String DATABASE_CREATE3 = "CREATE TABLE Permission (permission ENUM not null , description VARCHAR(200),   PRIMARY KEY(permission) ) ";
	private static final String DATABASE_CREATE4 = "CREATE TABLE Type_ (type_ ENUM , description VARCHAR(200) ,PRIMARY KEY(type_));";
	private static final String DATABASE_CREATE5 = "CREATE TABLE Sensor (name VARCHAR(45) "
			+ " ,   description VARCHAR(200),   maxFrequency FLOAT NOT NULL,   minFrequency FLOAT NOT NULL,   PRIMARY KEY(name) ) ; ";
	private static final String DATABASE_CREATE6 = "CREATE TABLE ContextProvider (name VARCHAR(45)  ,   Type__type_ ENUM NOT NULL,   "
			+ "description VARCHAR(200),   PRIMARY KEY(name));";
	private static final String DATABASE_CREATE7 = "CREATE TABLE AggregateApplication (Aggregate__idAggregate INTEGER UNSIGNED NOT NULL, Application_name VARCHAR(45) NOT NULL, PRIMARY KEY(Aggregate__idAggregate, Application_name));";
	// private static final String DATABASE_CREATE8 =
	// "CREATE TABLE Aggregate_ (idAggregate INTEGER NOT NULL AUTOINCREMENT, Event_value VARCHAR BINARY NOT NULL, Event_CorePredicate_corePredicate VARCHAR NOT NULL, PRIMARY KEY(idAggregate));";
	private static final String DATABASE_CREATE8 = "CREATE TABLE Aggregate_ (idAggregate INTEGER UNSIGNED NOT NULL, LogicalFunction_logicalFunction VARCHAR NOT NULL, Event_Function__name VARCHAR(45) NOT NULL, Event_value VARCHAR NOT NULL, Event_CorePredicate_corePredicate VARCHAR NOT NULL, Event_Function__ContextProvider_name VARCHAR(45) NOT NULL, PRIMARY KEY(idAggregate, LogicalFunction_logicalFunction, Event_Function__name, Event_value, Event_CorePredicate_corePredicate, Event_Function__ContextProvider_name));";
	private static final String DATABASE_CREATE9 = "CREATE TABLE Application (name VARCHAR(45) NOT NULL,Type__type_ ENUM NOT NULL,description TEXT NULL,PRIMARY KEY(name));";
	private static final String DATABASE_CREATE10 = "CREATE TABLE ApplicationPermission (Permission_permission ENUM NOT NULL,Application_name VARCHAR(45) NOT NULL, PRIMARY KEY(Permission_permission, Application_name));";
	private static final String DATABASE_CREATE11 = "CREATE TABLE ContextProviderPermission (ContextProvider_name VARCHAR(45) NOT NULL,Permission_permission ENUM NOT NULL, PRIMARY KEY(ContextProvider_name, Permission_permission));";
	private static final String DATABASE_CREATE12 = "CREATE TABLE Data_ (Function__name VARCHAR(45) NOT NULL, Function__ContextProvider_name VARCHAR(45) NOT NULL, timestamp timestamp NOT NULL, accuracy FLOAT NOT NULL, value VARCHAR(20) NOT NULL,PRIMARY KEY(timestamp, Function__name, Function__ContextProvider_name ));";
	private static final String DATABASE_CREATE13 = "CREATE TABLE Event (Function__name VARCHAR(45) NOT NULL,value VARCHAR NOT NULL, CorePredicate_corePredicate VARCHAR NOT NULL, Function__ContextProvider_name VARCHAR(45) NOT NULL, PRIMARY KEY(Function__name, value, CorePredicate_corePredicate, Function__ContextProvider_name));";
	private static final String DATABASE_CREATE14 = "CREATE TABLE FunctionSensors (Sensor_name VARCHAR(45) NOT NULL,Function__ContextProvider_name VARCHAR(45) NOT NULL,Function__name VARCHAR(45) NOT NULL,PRIMARY KEY(Sensor_name, Function__ContextProvider_name, Function__name));";
	private static final String DATABASE_CREATE15 = "CREATE TABLE Function_ (name VARCHAR(45) NOT NULL,ContextProvider_name VARCHAR(45) NOT NULL, description VARCHAR(200) NULL, frequency FLOAT NOT NULL,maxFrequency FLOAT NOT NULL, minFrequency FLOAT NOT NULL,PRIMARY KEY(name, ContextProvider_name));";

	private static ContextRepositoryImpl ContextRepositoryInstance = null;
	private static ApplicationRepositoryImpl ApplicationRepositoryInstance = null;
	private static DataRepositoryImpl DataRepositoryInstance = null;
	private static DataListenerImpl dataListener = null;
	private static EventRepositoryImpl EventRepositoryInstance = null;
	private static AggregateApplication AggregateApplicationInstance = null;
	private static AggregatorRepository AggregatorRepositoryInstance = null;
	private static DataBase instance;

	public static ContextRepositoryImpl getContextRepositoryInstance() {
		DataBase database = DataBase.getInstance();
		if (ContextRepositoryInstance == null) {
			ContextRepositoryInstance = database.new ContextRepositoryImpl();
		}
		return ContextRepositoryInstance;
	}

	public static ApplicationRepositoryImpl getApplicationRepositoryInstance() {
		DataBase database = DataBase.getInstance();
		if (ApplicationRepositoryInstance == null) {
			ApplicationRepositoryInstance = database.new ApplicationRepositoryImpl();
		}
		return ApplicationRepositoryInstance;
	}

	public static DataRepositoryImpl getDataRepositoryInstance() {
		DataBase database = DataBase.getInstance();
		if (DataRepositoryInstance == null) {
			DataRepositoryInstance = database.new DataRepositoryImpl();
			dataListener = DataListenerImpl.getInstance();
		}
		return DataRepositoryInstance;
	}

	public static EventRepositoryImpl getEventRepositoryInstance() {
		DataBase database = DataBase.getInstance();
		if (EventRepositoryInstance == null) {
			EventRepositoryInstance = database.new EventRepositoryImpl();
		}
		return EventRepositoryInstance;
	}

	public static AggregateApplication getAggregateApplicationInstance() {
		DataBase database = DataBase.getInstance();
		if (AggregateApplicationInstance == null) {
			AggregateApplicationInstance = database.new AggregateApplication();
		}
		return AggregateApplicationInstance;
	}

	public static AggregatorRepository getAggregatorRepositoryInstance() {
		DataBase database = DataBase.getInstance();
		if (AggregatorRepositoryInstance == null) {
			AggregatorRepositoryInstance = database.new AggregatorRepository();
		}
		return AggregatorRepositoryInstance;
	}

	public DataBase() {
	}

	public static DataBase getInstance() {

		if (instance == null) {
			instance = new DataBase();
		}
		return instance;

	}

	static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE1);
			db.execSQL(DATABASE_CREATE2);
			db.execSQL(DATABASE_CREATE3);
			db.execSQL(DATABASE_CREATE4);
			db.execSQL(DATABASE_CREATE5);
			db.execSQL(DATABASE_CREATE6);
			db.execSQL(DATABASE_CREATE7);
			db.execSQL(DATABASE_CREATE8);
			db.execSQL(DATABASE_CREATE9);
			db.execSQL(DATABASE_CREATE10);
			db.execSQL(DATABASE_CREATE11);
			db.execSQL(DATABASE_CREATE12);
			db.execSQL(DATABASE_CREATE13);
			db.execSQL(DATABASE_CREATE14);
			db.execSQL(DATABASE_CREATE15);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Content provider database",
					"Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + "logicalFunction");
			db.execSQL("DROP TABLE IF EXISTS " + "CorePredicate");
			db.execSQL("DROP TABLE IF EXISTS " + "Permission");
			db.execSQL("DROP TABLE IF EXISTS " + "Type_");
			db.execSQL("DROP TABLE IF EXISTS " + "Sensor");
			db.execSQL("DROP TABLE IF EXISTS " + "ContextProvider");
			db.execSQL("DROP TABLE IF EXISTS " + "AggregateApplication");
			db.execSQL("DROP TABLE IF EXISTS " + "Aggregate_");
			db.execSQL("DROP TABLE IF EXISTS " + "Application");
			db.execSQL("DROP TABLE IF EXISTS " + "ApplicationPermission");
			db.execSQL("DROP TABLE IF EXISTS " + "ContextProviderPermission");
			db.execSQL("DROP TABLE IF EXISTS " + "Data_");
			db.execSQL("DROP TABLE IF EXISTS " + "Event");
			db.execSQL("DROP TABLE IF EXISTS " + "FunctionSensors");
			db.execSQL("DROP TABLE IF EXISTS " + "Function_");
			onCreate(db);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contextsection.ApplicationRepository#delete(android.net.Uri,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case REPOSITORY:
			count = repositoryDB.delete(DATABASE_TABLE, selection,
					selectionArgs);
			break;
		case REPOSITORY_ID:
			String id = uri.getPathSegments().get(1);
			count = repositoryDB.delete(DATABASE_TABLE, ID
					+ " = "
					+ id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contextsection.ApplicationRepository#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		// get all repository
		case REPOSITORY:
			return "vnd.android.cursor.dir/vnd.contentexample.Repository.repository";
			// get a particular repository
		case REPOSITORY_ID:
			return "vnd.android.cursor.item/vnd.contentexample.Repository.repository";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contextsection.ApplicationRepository#insert(android.net.Uri,
	 * android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Context c = getContext();
		// add a new context
		long rowID = repositoryDB.insert(DATABASE_TABLE, "", values);

		// if added successfully
		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			if (c != null)
				c.getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contextsection.ApplicationRepository#onCreate()
	 */
	@Override
	public boolean onCreate() {

		dbHelper = new DatabaseHelper(this.getContext());
		repositoryDB = dbHelper.getWritableDatabase();
		if (dbHelper == null)
			return false;
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contextsection.ApplicationRepository#query(android.net.Uri,
	 * java.lang.String[], java.lang.String, java.lang.String[],
	 * java.lang.String)
	 */

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Context cont = getContext();
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		sqlBuilder.setTables(DATABASE_TABLE);

		if (uriMatcher.match(uri) == REPOSITORY_ID)
			// if getting a particular context
			sqlBuilder.appendWhere(ID + " = " + uri.getPathSegments().get(1));

		if (sortOrder == null || sortOrder == "")
			sortOrder = FIELD;

		Cursor c = sqlBuilder.query(repositoryDB, projection, selection,
				selectionArgs, null, null, sortOrder);

		// register to watch a content URI for changes
		if (cont != null)
			c.setNotificationUri(cont.getContentResolver(), uri);
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contextsection.ApplicationRepository#update(android.net.Uri,
	 * android.content.ContentValues, java.lang.String, java.lang.String[])
	 */

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case REPOSITORY:
			count = repositoryDB.update(DATABASE_TABLE, values, selection,
					selectionArgs);
			break;
		case REPOSITORY_ID:
			count = repositoryDB.update(
					DATABASE_TABLE,
					values,
					ID
							+ " = "
							+ uri.getPathSegments().get(1)
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	public boolean consultaSiExisteCampo(String dataTable, String id,
			String field, String eq) {
		DATABASE_TABLE = dataTable;
		ID = id;
		FIELD = field;
		Cursor c = query(CONTENT_URI, null, null, null, field);
		String subscript = "";
		SortedSet<String> s = new TreeSet<String>();
		if (c.moveToFirst()) {
			do {
				// Obtener los valores del campo
				subscript = c.getString(c.getColumnIndex(field));
				s.add(subscript);
			} while (c.moveToNext());
		}
		return s.contains(eq);

	}

	public boolean consultaSiExisteCampo(String dataTable, String id,
			String field, List<?> eq) {
		boolean res = true;
		Iterator<?> it = eq.iterator();

		while (it.hasNext()) {
			if (!consultaSiExisteCampo(dataTable, id, field, it.next()
					.toString())) {
				res = false;
			}
		}
		return res;
	}

	public boolean consultaSiExisteCampo2(String dataTable, String id,
			String field1, String field2, String eq1, String eq2) {
		boolean res = false;
		DATABASE_TABLE = dataTable;
		ID = id;
		Cursor c = query(CONTENT_URI, null, null, null, field1);
		String subscript = "";
		String subscript2 = "";
		if (c.moveToFirst()) {
			do {
				// Obtener los valores del campo
				subscript = c.getString(c.getColumnIndex(field1));
				subscript2 = c.getString(c.getColumnIndex(field2));
				if (subscript.equals(eq1) && subscript2.equals(eq2)) {
					res = true;
				}
			} while (c.moveToNext());
		}
		return res;

	}

	public void insertaEnTabla(ContentValues values, String table) {
		DATABASE_TABLE = table;
		insert(CONTENT_URI, values);
	}

	private class ContextRepositoryImpl implements ContextRepository {

		private ContextRepositoryImpl() {
		}

		// Metodo observador para saber que context providers se encuentran
		// subscritos al core.
		/*
		 * (non-Javadoc)
		 * 
		 * @see com.contextsection.ContextRepository#getContextProviders()
		 */
		@Override
		public SortedSet<String> getContextProviders() {

			DATABASE_TABLE = "ContextProvider";
			ID = "name";
			FIELD = "name";
			Cursor c = query(CONTENT_URI, null, null, null, "name");
			String subscript = "";
			SortedSet<String> s = new TreeSet<String>();
			if (c.moveToFirst()) {
				do {
					// Obtener los valores del campo
					subscript = c.getString(c.getColumnIndex("name"));
					s.add(subscript);
				} while (c.moveToNext());
			}

			return s;
		}

		// Añade un nuevo Context Provider a la subscripcion
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.contextsection.ContextRepository#addAccessStorage(java.lang.String
		 * )
		 */
		@Override
		public boolean addAccessStorage(IContextInformationCore context) {
			String idContextProvider = context.getName();
			String type = context.getType();
			List<String> permission = context.getPermissions();
			String description = context.getDescription();
			List<String> sensor = context.getSensors();
			List<FunctionInformationImpl> function = context.getFunctions();
			float frequency = context.getFrequency();
			float maxFrequency = context.getMaxFrequency();
			float minFrequency = context.getMinFrequency();

			// Este primer if es para comprobar que tipo de modificacion se va a
			// hacer para despues informar al listener

			if (getContextProviders().contains(idContextProvider)
					&& getContextProviders() == null) {
				change = Change.UPDATE;
			} else {
				change = Change.INSERT;
			}

			ContentValues values = new ContentValues();

			ContentValues values2 = new ContentValues();

			ContentValues values3 = new ContentValues();

			ContentValues values4;

			values.put("name", idContextProvider);
			values.put("Type__type_", type);
			values.put("description", description);

			if (!getContextProviders().contains(idContextProvider))
				values2.put("ContextProvider_name", idContextProvider);

			Iterator<String> it1 = permission.iterator();

			while (it1.hasNext()) {
				values2.put("Permission_permission", it1.next());
			}

			Iterator<FunctionInformationImpl> it2 = function.iterator();

			values3.put("ContextProvider_name", idContextProvider);

			while (it2.hasNext()) {
				String nameFunction = it2.next().getName();
				values3.put("name", nameFunction);
				values3.put("description", description);
				values3.put("frequency", frequency);
				values3.put("maxFrequency", maxFrequency);
				values3.put("minFrequency", minFrequency);

				if (consultaSiExisteCampo("Sensor", "name", "name", sensor)
						&& !consultaSiExisteCampo("Function_",
								idContextProvider, "name", nameFunction)) {
					insertaEnTabla(values3, "Function_");

					for (FunctionInformationImpl functionAux : function) {
						values4 = new ContentValues();
						values4.put("Function__ContextProvider_name",
								idContextProvider);
						values4.put("Function__name", functionAux.getName());

						for (String sensorAux : functionAux.getSensors()) {
							values4.put("Sensor_name", sensorAux);
						}

						insertaEnTabla(values4, "FunctionSensors");
					}
				}
			}

			if (consultaSiExisteCampo("Type_", "type_", "type_", type)
					&& consultaSiExisteCampo("Permission", "permission",
							"permission", permission)
					&& !getContextProviders().contains(idContextProvider)) {
				insertaEnTabla(values, "ContextProvider");
				insertaEnTabla(values2, "ContextProviderPermission");
			}

			try {
				onChange(Change.INSERT, idContextProvider);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		// Avisar al módulo suscrito (Context Listener) en el caso de que se
		// produzca una actualización en los datos que hay almacenados en el
		// módulo.
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.contextsection.ContextRepository#onChange(com.contextsection.
		 * Change, java.lang.String)
		 */
		@Override
		public void onChange(Change change, String idContextProvider) {
			SortedSet<String> cp = getContextProviders();
			IContextListener inst = ContextListenerImpl.getInstance();
			inst.onContextRepositoryUpdate(cp);
		}

		/*
		 * Cuando el módulo 'Context Subscription' solicite la eliminación de un
		 * 'Context Provider' dentro del repositorio eliminará este registro y
		 * enviará un nuevo aviso de eliminación a través del método 'onChange'
		 * al módulo 'Context Listener' con el identificador del 'Context
		 * Provider' asociado.
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.contextsection.ContextRepository#deleteContextProvider(java.lang.
		 * String)
		 */
		@Override
		public void deleteContextProvider(String idContextProvider)
				throws Exception {
			String st[] = { idContextProvider };
			DATABASE_TABLE = "ContextProvider";
			delete(CONTENT_URI, "name", st);
			DATABASE_TABLE = "ContextProviderPermission";
			delete(CONTENT_URI, "ContextProvider_name", st);
			DATABASE_TABLE = "Function_";
			delete(CONTENT_URI, "ContextProvider_name", st);
			DATABASE_TABLE = "FunctionSensors";
			delete(CONTENT_URI, "Function__ContextProvider_name", st);
			onChange(Change.DELETE, idContextProvider);

		}

	}

	public class ApplicationRepositoryImpl implements ApplicationRepository {
		private ApplicationRepositoryImpl() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.contextsection.ApplicationRepository#getContextProviders()
		 */
		@Override
		public SortedSet<String> getApplications() {

			DATABASE_TABLE = "Application";
			ID = "name";
			FIELD = "name";
			Cursor c = query(CONTENT_URI, null, null, null, "name");
			String subscript = "";
			SortedSet<String> s = new TreeSet<String>();
			if (c.moveToFirst()) {
				do {
					// Obtener los valores del campo
					subscript = c.getString(c.getColumnIndex("name"));
					s.add(subscript);
				} while (c.moveToNext());
			}

			return s;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.contextsection.ApplicationRepository#addAccessStorage(java.lang
		 * .String)
		 */
		@Override
		public boolean addAllowedApplication(String idApplication, String type,
				String permission, String description) {

			ContentValues values1 = new ContentValues();
			values1.put("Permission_permission", permission);
			values1.put("Application_name", idApplication);

			ContentValues values2 = new ContentValues();
			values2.put("name", idApplication);
			values2.put("Type__type_", type);
			values2.put("description", description);

			if (consultaSiExisteCampo("Type_", "type_", "type_", type)
					&& consultaSiExisteCampo("Permission", "permission",
							"permission", permission)
					&& !getApplications().contains(idApplication)) {
				Log.i("APP", "IF");
				insertaEnTabla(values1, "ApplicationPermission");
				insertaEnTabla(values2, "Application");
				Log.w("ADDALLOWEDAPPLICATION", "ENTRY");
			}

			else {
				Log.i("APP", "NO IF");
				Log.i("TYPE", " " + type);
				Log.i("Permission", " " + permission);
				Log.i("application", idApplication);
			}

			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.contextsection.ApplicationRepository#deleteContextProvider(java
		 * .lang. String)
		 */
		@Override
		public boolean removeAllowedApplication(String idApplication) {
			String st[] = { idApplication };
			DATABASE_TABLE = "Application";
			delete(CONTENT_URI, "name", st);
			DATABASE_TABLE = "ApplicationPermission";
			delete(CONTENT_URI, "Application_name", st);
			return true;
		}

		@Override
		public boolean isApplicationAllowed(String idApplication) {
			return getApplications().contains(idApplication);
		}

	}

	private class DataRepositoryImpl extends Observable implements
			IDataRepository {
		private Map<Pair<String, String>, IDataContextCore> accessStorage;

		private DataRepositoryImpl() {
		}

		// Metodo observador para saber que Data providers se encuentran
		// subscritos al core.
		/*
		 * (non-Javadoc)
		 * 
		 * @see com.Datasection.DataRepository#getDataProviders()
		 */
		@Override
		public IDataContextCore getAccessStorage(String idContextProvider,
				String idFunction) {

			Pair<String, String> par = new Pair<String, String>(
					idContextProvider, idFunction);
			return accessStorage.get(par);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.Datasection.DataRepository#addAccessStorage(java.lang.String)
		 */
		@Override
		public void addAccessStorage(IDataContextCore data) {
			ContentValues values = new ContentValues();
			values.put("Function__name", data.getIdFunction());
			values.put("Function__ContextProvider_name",
					data.getIdContextProvider());
			values.put("timestamp", data.getTimeStamp());
			values.put("accuracy", data.getAccuracy());

			// TODO Investigar como almacenar objetos en mysqlite
			String value = data.getValue().toString();
			if (!(value == null)) {
				values.put("value", value);

				Log.i("Data",
						data.getIdFunction() + data.getIdContextProvider()
								+ data.getTimeStamp() + data.getAccuracy()
								+ data.getValue().toString());
				if (consultaSiExisteCampo2("Function_",
						"name, ContextProvider_name", "name",
						"ContextProvider_name", data.getIdFunction(),
						data.getIdContextProvider())) {

					Log.i("VALUES", values.toString());
					insertaEnTabla(values, "Data_");
				}
			} else {
				Log.w("Value", "Empty Value");
			}
			garbageCollector();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.Datasection.DataRepository#onChange(com.Datasection.Change,
		 * java.lang.String)
		 */
		// COMUNICAR EL NUEVO DATO AL LISTENER
		@Override
		public void onChange(IDataContext dataContext) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.datasection.IDataRepository#garbageCollector()
		 */

		@Override
		public void garbageCollector() {
			long actualTime = System.currentTimeMillis();
			DATABASE_TABLE = "Data_";

			// Para la selección de la clausula WHERE, he elegido que la
			// diferencia de tiempo sea de 5 Horas (18.000.000 ms)
			String selection = actualTime + " - timestamp >= '18000000'";

			int rowsEliminated = delete(DataBase.CONTENT_URI, selection, null);
			Log.i("DataRepository.garbageCollector", "Se han eliminado "
					+ rowsEliminated + " tuplas");

			// TODO Añadir referencia a los objetos eliminados
			// TODO Borrar la mierda esta
			IDataContextCore dataContextAux = new DataContextCoreImpl("DELETE");
			dataListener.onDataRepositoryUpdate(dataContextAux, "DELETE");
		}

		@Override
		public List<IDataContextCore> getAccessStorage(long timestampInit,
				long timestampEnd) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Uri insertDataRepository(Uri uri, ContentValues contentValues,
				IDataContext dataContext) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int updateDataRepository(Uri uri, ContentValues values,
				String selection, String[] selectionArgs,
				IDataContext dataContext) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int deleteDataRepository(Uri uri, String selection,
				String[] selectionArgs, IDataContext dataContext) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	private class EventRepositoryImpl implements IEventRepository {
		private EventRepositoryImpl() {
			pair = new HashMap<String, EventCoreImpl>();
			IDEventCounter = 0L;
		}

		private Map<String, EventCoreImpl> pair;
		private Long IDEventCounter;

		@Override
		public String nextIdEvent() {
			IDEventCounter++;
			String idEvent = String.valueOf(IDEventCounter);
			return idEvent;
		}

		@Override
		public EventCoreImpl getEvent(String idEvent) {
			EventCoreImpl event = null;
			if (pair.containsKey(idEvent))
				event = pair.get(idEvent);
			return event;
		}

		@Override
		public String addEvent(EventCoreImpl event) {
			String idEvent = null;

			// Entra en el if si el evento ya está en el repositorio
			// En cuyo caso obtiene el idEvent correspondiente y lo devuelve
			if (pair.containsValue(event)) {
				Set<String> keySet = pair.keySet();
				for (String key : keySet) {
					EventCoreImpl eventAux = pair.get(key);
					if (eventAux.equals(event)) {
						idEvent = key;
						break;
					}
				}
			} else {
				// Entra en el else cuando el evento no está en el repositorio
				// En cuyo caso hay que añadirlo como nuevo

				// 1) Añadimos el evento y su id al mapa llamado "pair"
				idEvent = nextIdEvent();
				pair.put(idEvent, event);

				// 2) Convertimos el objeto Predicate del IEvent en array de
				// bytes
				// ByteArrayOutputStream bs1 = new ByteArrayOutputStream();
				// ObjectOutputStream os1;
				// try {
				// os1 = new ObjectOutputStream(bs1);
				// os1.writeObject(event.getCorePredicate());
				// os1.close();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// byte[] predicateConvertidoABytes = bs1.toByteArray();

				// 3) Convertimos el campo "value" del evento en array de bytes

				/*
				 * TODO estas líneas van a generar problemas debido a que en la
				 * definición de la tabla Event de la base de datos (atributo
				 * DATABASE_CREATE13) el tipo value se ha definido como VARCHAR.
				 * Sin embargo, este atributo de la clase event es de tipo T,
				 * por lo que no tiene por que ser una cadena de texto
				 */
				ByteArrayOutputStream bs2 = new ByteArrayOutputStream();
				try {
					ObjectOutputStream os2 = new ObjectOutputStream(bs2);
					os2.writeObject(event.getValue());
					os2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				byte[] valueConvertidoABytes = bs2.toByteArray();

				// 4) Añadimos el evento (Solo el evento) a la base de datos
				ContentValues values = new ContentValues();
				values.put("Function__name", event.getIdFunction());
				values.put("value", event.getValue().toString());
				values.put("CorePredicate_corePredicate", event
						.getCorePredicate().toString());
				values.put("Function__ContextProvider_name",
						event.getIdContext());

				// Log.w("---------------------------", event.getIdFunction()
				// .toString()
				// + valueConvertidoABytes.toString()
				// + event.getCorePredicate().toString()
				// + event.getIdContext().toString());

				insertaEnTabla(values, "Event");
			}
			return idEvent;
		}

		@Override
		public EventCoreImpl removeEvent(String IdEvent) {
			EventCoreImpl eventoSalida;
			if (pair.containsKey(IdEvent)) {
				// 1) Eliminamos del mapa
				eventoSalida = pair.remove(IdEvent);

				// 2) Eliminamos de la base de datos
				// 2.1) Convertimos el objeto Predicate del eventoSalida en
				// array de bytes
				ByteArrayOutputStream bs1 = new ByteArrayOutputStream();
				try {
					ObjectOutputStream os1 = new ObjectOutputStream(bs1);
					os1.writeObject(eventoSalida.getCorePredicate());
					os1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				byte[] predicateConvertidoABytes = bs1.toByteArray();

				// 2.2) Convertimos el campo "value" del evento en array de
				// bytes
				ByteArrayOutputStream bs2 = new ByteArrayOutputStream();
				try {
					ObjectOutputStream os2 = new ObjectOutputStream(bs2);
					os2.writeObject(eventoSalida.getValue());
					os2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				byte[] valueConvertidoABytes = bs2.toByteArray();

				// 2.3) Removemos el objeto de la base de datos
				String selection = "Function__name = '"
						+ eventoSalida.getIdFunction() + "' AND value = '"
						+ valueConvertidoABytes.toString()
						+ "' AND CorePredicate_corePredicate = '"
						+ predicateConvertidoABytes.toString()
						+ "' AND Function__ContextProvider_name = '"
						+ eventoSalida.getIdContext() + "'";

				DATABASE_TABLE = "Event";
				delete(DataBase.CONTENT_URI, selection, null);

			} else {
				throw new RuntimeException(
						"El idEvent no se corresponde con ningún elemento del repositorio");
			}
			return eventoSalida;
		}

		@Override
		public Map<String, EventCoreImpl> getEventsforData(
				Integer idContextProvider, String function) {
			Map<String, EventCoreImpl> mapaSalida = new HashMap<String, EventCoreImpl>();
			for (String key : pair.keySet()) {
				EventCoreImpl event = pair.get(key);
				if (event.getIdContext().equals(idContextProvider)
						&& event.getIdFunction().equals(function))
					;
				mapaSalida.put(key, event);
			}
			return mapaSalida;
		}

	}

	/*
	 * ATENCION!!!! Al haber ambigüedad en la documentación he tenido en cuenta
	 * para implementar esta clase las siguientes características:
	 * 
	 * 1) El breaking dance 2) El Michael Jackson 3) La tabla correspondiente a
	 * este repositorio (AggregateApplication, línea 81 de este documento) la he
	 * interpretado como una tabla que contiene dos campos: idApplication e
	 * idAggregate. ¿Por qué? Semánticamente este repositorio solo enlaza una
	 * aplicacón con un agregado (solo las id's, ya que los objetos ya existen
	 * en otros repositorios), así que en la práctica, solo es necesario guardar
	 * las relaciones entre Apps y Agregados. Aparte de este motivo, en el
	 * diagrama tecnológico que aparece en el archivo PFC-final.pptx que me
	 * mandaste, la clase que implemento a continuación no solicita ningún dato
	 * a ninguna otra clase, por lo que si un método de esta clase (AppX) recibe
	 * un idApplication y un idAggregate, esto será lo único que puede guardar.
	 * Por si estos motivos te parecen poco, citaré textualmente un fragmento de
	 * la documentación: "El módulo 'AppX' añade el par (identificador de
	 * aplicación, identificador de agregado) pasado como argumento a través de
	 * su punto de entrada. Posteriormente devuelve un valor booleano al proceso
	 * cliente indicando que se ha realizado la adición correctamente. Después,
	 * devuelve el control del proceso al cliente."
	 * 
	 * Estas son las razones por las que creo que la creación de la tabla
	 * AggregateApplication no es correcta del todo
	 */
	public class AggregateApplication implements IAppX {
		private Map<Integer, Set<String>> pair;

		private AggregateApplication() {
			pair = new HashMap<Integer, Set<String>>();
			Integer key, keyOld = -1;
			Set<String> value = new HashSet<String>();

			DATABASE_TABLE = "AggregateApplication";
			ID = "Aggregate__idAggregate";
			FIELD = "Aggregate__idAggregate, Application_name";
			Cursor c = query(CONTENT_URI, null, null, null,
					"Aggregate__idAggregate");
			String subscript = "";
			SortedSet<String> s = new TreeSet<String>();
			if (c.moveToFirst()) {
				do {
					// Obtener los valores del campo
					key = c.getInt(0);

					// la primera vuelta se igualan los valores para no añadir
					// de mas
					if (keyOld == -1) {
						keyOld = key;
					}

					s.add(subscript);

					if (key != keyOld) {
						pair.put(keyOld, value);
						value = new HashSet<String>();
					}
					value.add(c.getString(1));
					keyOld = key;
				} while (c.moveToNext());

				pair.put(key, value);
			}
		}

		@Override
		public Set<String> getApplicationsForAggregate(Integer idAggregate) {
			Set<String> aux = new HashSet<String>();
			aux = pair.get(idAggregate);
			return aux;
		}

		@Override
		public Set<Integer> getAggreggatesForApplication(String idApplication) {
			Set<Integer> aggregates = new HashSet<Integer>();
			for (Integer idAggregate : pair.keySet()) {
				Set<String> applicationPerAggregate = pair.get(idAggregate);
				if (applicationPerAggregate.contains(idApplication)) {
					aggregates.add(idAggregate);
				}
			}

			return aggregates;
		}

		@Override
		public Boolean addNewAggregatesSubscription(Integer idAggregate,
				String idApplication) {
			Boolean result = false;
			Set<String> auxiliar = new HashSet<String> ();
			Log.i("ADD", idAggregate + idApplication + "j" + pair.get(0));
			
			auxiliar.addAll(pair.get(idAggregate));
			if (!auxiliar.contains(idApplication)) {
				// 1) Agregar el par idAgregado - idAplicacion al mapa llamado
				// "pair"
				Set<String> applications = new HashSet<String>();
				applications.add(idApplication);
				pair.put(idAggregate, applications);

				/*
				 * if (pair.containsKey(idAggregate)){ Set<String>
				 * idApplications = pair.get(idAggregate); if
				 * (idApplications.contains(idApplication)){ } else {
				 * idApplications.add(idApplication); pair.put(idAggregate,
				 * idApplications); } } else{ Set<String> idApplications = new
				 * HashSet<String>(); idApplications.add(idApplication);
				 * pair.put(idAggregate,idApplications); }
				 */

				// 2) Agregar el par idAgregado - idAplicacion a la base de
				// datos
				ContentValues values = new ContentValues();
				values.put("Aggregate__idAggregate", idAggregate);
				values.put("Application_name", idApplication);

				try {
					insertaEnTabla(values, "AggregateApplication");
				} catch (Exception e) {
					Log.w("AggregateApplication", "Cannot add row");
				}
			}
			return result;
		}

		@Override
		public Boolean removeAggregateSubscription(Integer idAggregate) {
			Boolean result = false;
			// 1) Elimina el agregado del mapa llamado "pair"
			pair.remove(idAggregate);

			// 2) Elimina el agregado de la base de datos
			String selection = "Aggregate__idAggregate = '" + idAggregate + "'";
			int rowsAffected = delete(DataBase.CONTENT_URI, selection, null);
			if (rowsAffected > 0)
				result = true;

			return result;
		}

		@Override
		public Boolean addSubscriptionToAggregate(Integer idAggregate,
				String idApplication) {
			Boolean result = false;
			if (pair.containsKey(idAggregate)) {
				Set<String> applications = getApplicationsForAggregate(idAggregate);
				if (!applications.contains(idApplication)) {
					result = addNewAggregatesSubscription(idAggregate,
							idApplication);
				}
			} else {
				result = addNewAggregatesSubscription(idAggregate,
						idApplication);
			}
			return result;
		}

		@Override
		public Boolean removeSubscriptionToAggregate(Integer idAggregate,
				String idApplication) {
			Boolean result = false;

			// Eliminación de la aplicacion del mapa "pair"
			if (pair.containsKey(idAggregate)) {
				Set<String> applications = pair.get(idAggregate);
				if (applications.contains(idApplication)) {
					applications.remove(idApplication);
					if (applications.size() == 0) {
						pair.remove(idAggregate);
					} else {
						pair.put(idAggregate, applications);
					}
				}

			}

			// Eliminación de la aplicación de la base de datos
			String selection = "Application_idApplication = '" + idApplication
					+ "' AND Aggregate__idAggregate = '" + idAggregate + "'";
			int rowsAffected = delete(DataBase.CONTENT_URI, selection, null);
			if (rowsAffected > 0)
				result = true;

			return result;
		}

		@Override
		public Boolean removeApplication(String idApplication) {
			Boolean result = false;
			// 1) Se elimina en primer lugar el idApplication del mapa "pair"
			Set<Integer> aggregates = getAggreggatesForApplication(idApplication);
			for (Integer aggregate : aggregates) {
				Set<String> applications = pair.get(aggregate);
				applications.remove(idApplication);
				pair.put(aggregate, applications);
			}

			// 2) Se elimina el idApplication de la base de datos
			String selection = "Application_idApplication = '" + idApplication
					+ "'";
			int rowsAffected = delete(DataBase.CONTENT_URI, selection, null);
			if (rowsAffected > 0)
				result = true;

			return result;
		}

		@Override
		public Boolean isApplicationAllowedToContext(String idApplication,
				String idContext) {
			Set<String> contexts;
			Boolean res = false;

			contexts = pair.get(idApplication);
			if (contexts.contains(idContext)) {
				res = true;
			}
			return res;
		}

	}

	public class AggregatorRepository implements IAggregatorRepository {

		private AggregatorRepository() {
		}

		// Por favor mirad esto que seguro que peta
		// TODO
		@Override
		public <T> IAggregateCore getAggreggate(Integer idAggregate) {
			IAggregateCore res;
			DATABASE_TABLE = "Aggregate_";
			ID = "idAggregate";
			FIELD = "aggregate_";

			String[] campos = new String[] { "idAggregate" };
			String[] args = new String[] { idAggregate.toString() };

			Cursor c = query(CONTENT_URI, campos, "idAggregate=?", args, null);
			Object obj = (Object) c.getBlob(c.getColumnIndex("aggregate_"));

			res = (IAggregateCore) obj;

			return res;
		}

		@Override
		public String addAggreggate(IAggregateCore aggreggate) {
			Integer idAggregate = 0;

			HashMap<IAggregatePredicate, List<EventCoreImpl>> aggregatePredicates = aggreggate
					.getAggregatePredicates();

			for (IAggregatePredicate aggregatePredicate : aggregatePredicates
					.keySet()) {
				List<EventCoreImpl> eventPredicate = aggregatePredicates
						.get(aggregatePredicate);
				for (EventCoreImpl event : eventPredicate) {
					ContentValues values = new ContentValues();
					values.put("idAggregate", idAggregate);
					values.put("LogicalFunction_logicalFunction",
							aggregatePredicate.toString());
					values.put("Event_Function__name", event.getIdFunction());
					values.put("Event_value", event.getValue().toString());
					values.put("Event_CorePredicate_corePredicate", event
							.getCorePredicate().toString());
					values.put("Event_Function__ContextProvider_name",
							event.getIdContext());

					insertaEnTabla(values, "Aggregate_");
				}
			}

			return aggreggate.toString();
		}

		@Override
		public void removeAggreggate(Integer idAggreggate) {
			String st[] = { idAggreggate.toString() };
			DATABASE_TABLE = "Aggregate_ ";
			delete(CONTENT_URI, "idAggreggate", st);

		}

	}

}
