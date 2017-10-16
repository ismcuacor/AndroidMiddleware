package us.es.contextProvider;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import us.es.interfaces.ISender;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

enum destinos {
	ContextSubscription, ContextUpdater
};

public class SenderTimerImpl extends Activity implements ISender {

	Integer id = 0; // Este id se generara mas tarde con el criptosistema
					// propuesto
	Bundle bundle;
	destinos destino;

	public SenderTimerImpl() {
		bundle = new Bundle();
	}

	public SenderTimerImpl(String destino) {
		bundle = new Bundle();
		this.destino = getDestino(destino);
	}

	public SenderTimerImpl(String destino, Bundle bundle) {
		this.bundle = bundle;
		this.destino = getDestino(destino);
	}

	public SenderTimerImpl(String destino, Serializable message) {
		bundle = new Bundle();
		bundle.putSerializable((id++).toString(), message);
		this.destino = getDestino(destino);
	}

	public destinos getDestino(String destino) {
		destinos res = null;
		if (destino == "Subscription")
			res = destinos.ContextSubscription;
		else if (destino == "Updater")
			res = destinos.ContextUpdater;
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see us.es.contextSection.ISender#write(java.io.Serializable)
	 */
	@Override
	public void write(Serializable message) {
		bundle.putSerializable((id++).toString(), (Serializable) message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see us.es.contextSection.ISender#write(java.lang.String,
	 * java.io.Serializable)
	 */
	@Override
	public void write(String id, Serializable message) {
		bundle.putSerializable(id, (Serializable) message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see us.es.contextSection.ISender#send()
	 */
	@Override
	public void send() {
		Intent intent = new Intent();
		intent.putExtras(bundle);
		if (destino.equals(getDestino("Subscription"))) {
			intent.setClassName("com.contextsection",
					"com.contextsection.ContextSubscriptionImpl");
			Log.w("Jose", "holaDonPepito");
			Log.w("intent", intent.toString());
		} else if (destino.equals(getDestino("Updater"))) {
			intent.setClassName("com.contextsection",
					"com.contextsection.ContextUpdaterImpl");
			Log.w("Pepito", "holaDonJose");

		}
		intent.addCategory("android.intent.category.LAUNCHER");
		startActivity(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see us.es.contextSection.ISender#receive()
	 */
	@Override
	public Map<String, String> receive() {
		Map<String, String> res = new HashMap<String, String>();
		Bundle bundl = getIntent().getExtras();
		Set<String> s = bundl.keySet();
		if (s.size() > 0) {
			for (String key : s) {
				res.put(key, bundl.getString(key));
			}
		}
		return res;
	}
}