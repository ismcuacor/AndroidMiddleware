package com.datasectionInterface;

import com.contextsectionInterface.IDataContextCore;

public interface IDataListener {
	
//	public void subscribe(IContextAggregator observer);

	/* El m�dulo 'Data Listener' debe ofrecer la funcionalidad necesaria para recoger el mensaje y, adem�s,
	 *  reenviarlo al m�dulo 'Aggregation Repository'.*/
	public abstract void onDataRepositoryUpdate(IDataContextCore dataContext, String modification);

}