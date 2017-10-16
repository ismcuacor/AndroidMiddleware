package com.datasectionInterface;

import com.contextsectionInterface.IDataContextCore;

public interface IDataListener {
	
//	public void subscribe(IContextAggregator observer);

	/* El módulo 'Data Listener' debe ofrecer la funcionalidad necesaria para recoger el mensaje y, además,
	 *  reenviarlo al módulo 'Aggregation Repository'.*/
	public abstract void onDataRepositoryUpdate(IDataContextCore dataContext, String modification);

}