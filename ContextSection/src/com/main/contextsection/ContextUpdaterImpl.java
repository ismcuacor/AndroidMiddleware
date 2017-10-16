package com.main.contextsection;

import java.util.SortedSet;
import java.util.TreeSet;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.contextsectionInterface.IContextSubscription;
import com.contextsectionInterface.IContextUpdater;
import com.contextsectionInterface.IDataContextCore;
import com.main.database.DataBase;
import com.datasectionInterface.IDataRepository;

public class ContextUpdaterImpl extends Service implements IContextUpdater {
	private static IContextUpdater instance = null;
	private static IContextSubscription contextSubscription = ContextSubscriptionImpl
			.getInstance();
	private IDataRepository DataRepository = DataBase
			.getDataRepositoryInstance();

	// Se coloca una variable del mismo tipo que la clase llamada, por
	// convención "instance".
	// Aqui reside todo el secreto del patron SINGLETON ya que dicha variable es
	// la que se instancia
	// por unica vez y se devuelve al cliente.
	private static SortedSet<String> contextProviders = new TreeSet<String>();

	public static ContextUpdaterImpl getInstance() {
		if (instance == null) {
			instance = new ContextUpdaterImpl();
		}
		return (ContextUpdaterImpl) instance;
	}

	public void onCreate() {
		super.onCreate();
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle capture = intent.getExtras();

		if (capture != null) {
			if (capture.getParcelable("info") != null) {
				String idPaquete = capture.getString("idPaquete");
				DataContextImpl dataContext = capture.getParcelable("info");

				IDataContextCore data = new DataContextCoreImpl(idPaquete,
						dataContext.getIdFunction(), 2f, dataContext);
				updateContextData(data);
			} else if (capture.getString("subscription") != null) {
				String idPaquete = capture.getString("subscription");
				ContextInformationImpl contextInformation = capture
						.getParcelable("contextInformation");
				ContextInformationCoreImpl contextInformationCore = new ContextInformationCoreImpl(
						idPaquete, contextInformation);
				Boolean b = contextSubscription
						.onNewBundle(contextInformationCore);

				Intent newIntent = new Intent();
				newIntent.setClassName("us.es.contextProvider",
						"us.es.contextProvider.Main");
				newIntent.addCategory("android.intent.category.LAUNCHER");

				newIntent.putExtra("Accepted", b.toString());

				startService(newIntent);
			}
		}
		return START_NOT_STICKY;
	}

	// Para utilizar la unica instancia de la clase, los clientes deberan
	// invocar al metodo
	// getInstance(). Si se observa la condicion del if solo sera true la
	// primera vez.

	public SortedSet<String> getContextProviders() {
		return contextProviders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.contextsection.IContextUpdater#onContextChange(com.contextsection
	 * .DataContextImpl)
	 */
	@Override
	public void onContextChange(SortedSet<String> cp) {

		contextProviders = cp;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.contextsection.IContextUpdater#isContextAccepted(java.lang.Integer)
	 */
	@Override
	public boolean isContextAccepted(String idBundle) {
		boolean res = false;
		if (contextProviders.contains(idBundle))
			res = true;
		return res;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.contextsection.IContextUpdater#updateContextData(com.contextsection
	 * .DataContextImpl)
	 */
	@Override
	public boolean updateContextData(IDataContextCore data) {
		boolean res = false;
		if (isContextAccepted(data.getIdContextProvider())) {
			DataRepository.addAccessStorage(data);
			res = true;
		}

		// ISender send = new SenderImpl("Data");
		// Map<String, String> receive = send.receive();
		// send.write(data.getIdFunction(),data.getValue());
		// send.send();
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contextsection.IContextUpdater#dataToXML(com.contextsection.
	 * DataContextImpl)
	 */
	@Override
	public String dataToXML(IDataContextCore data) {
		String xml = "";
		return xml;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}