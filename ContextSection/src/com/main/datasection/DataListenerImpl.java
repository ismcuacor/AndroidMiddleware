package com.main.datasection;

import android.util.Log;

import com.aggregationsection.ContextAggregator;
import com.aggregationsectionInterface.IContextAggregator;
import com.contextsectionInterface.IDataContextCore;
import com.datasectionInterface.IDataListener;
import com.main.contextsection.DataContextCoreImpl;

public class DataListenerImpl implements IDataListener {
	
	private static IContextAggregator ContextAggregatorInstance = ContextAggregator.getInstance();
	
	/* El m�dulo 'Data Listener' debe ofrecer la funcionalidad necesaria para recoger el mensaje y, adem�s,
	 *  reenviarlo al m�dulo 'Aggregation Repository'.*/
	/* (non-Javadoc)
	 * @see com.datasection.IDataListener#onDataRepositoryUpdate(com.contextsectionInterface.IDataContextCore)
	 */
	
	private static DataListenerImpl instance;
	
	public static DataListenerImpl getInstance(){
		if (instance == null)
			instance = new DataListenerImpl();
		return instance;
	}

//	@Override
//	public void subscribe(IContextAggregator observer){
//		ContextAggregator = observer;
//	}
	
	@Override
	public void onDataRepositoryUpdate(IDataContextCore dataContext, String modification){
//TODO		Usar un modificador seg�n que modificaci�n
		IDataContextCore dataContextAux = new DataContextCoreImpl("1","1",1f,"1");
		ContextAggregatorInstance.onDataRepositoryChange(dataContextAux, modification);
	}
}
