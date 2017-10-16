package com.main.contextsection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.contextsectionInterface.ISender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
enum destinos {ContextRepository, DataRepository};


public class SenderImpl extends Activity implements ISender{
	Integer id = 0; //Este id se generara mas tarde con el criptosistema propuesto
	Bundle bundle;
	destinos destino;
	
	public SenderImpl(){
		bundle = new Bundle();
	}
	public SenderImpl(String destino){
		bundle = new Bundle();
		this.destino = getDestino(destino);
	}
	public SenderImpl(String destino, Bundle bundle){
		this.bundle = bundle;
		this.destino = getDestino(destino);
	}
	public SenderImpl(String destino, Serializable message){
		bundle = new Bundle();
		bundle.putSerializable((id++).toString(), message);
		this.destino = getDestino(destino);
	}
	
	public destinos getDestino(String destino){
		destinos res = null;
		if(destino == "Context")
			res = destinos.ContextRepository;
		else if(destino == "Data")
			res = destinos.DataRepository;
		return res;
	}

	/* (non-Javadoc)
	 * @see com.contextsection.ISender#write(java.io.Serializable)
	 */
	@Override
	public void write(Serializable message){
		bundle.putSerializable((id++).toString(),(Serializable) message);
	}
	/* (non-Javadoc)
	 * @see com.contextsection.ISender#write(java.lang.String, java.io.Serializable)
	 */
	@Override
	public void write(String id, Serializable message){
		bundle.putSerializable(id,(Serializable) message);
	}
	/* (non-Javadoc)
	 * @see com.contextsection.ISender#send()
	 */
	@Override
	public void send(){
		Intent intent = new Intent();
		intent.putExtras(bundle);
		if(destino == getDestino("Context")){
		intent.setClassName("com.contextsection", "com.contextsection.ContextRepository");
		}
		else if(destino == getDestino("Data")){
			intent.setClassName("com.datasection", "com.datasection.DataRepository");
		}
		intent.addCategory("android.intent.category.LAUNCHER");
		startActivity(intent);
	}
	/* (non-Javadoc)
	 * @see com.contextsection.ISender#receive()
	 */
	@Override
	public Map<String, String> receive(){
		Map<String,String> res = new HashMap<String, String>();
		Bundle bundl = getIntent().getExtras();
		Set<String> s = bundl.keySet();
		if(s.size() > 0){
			for (String key : s) {
				res.put(key, bundl.getString(key));
			}
		}
		return res;
	}
}

