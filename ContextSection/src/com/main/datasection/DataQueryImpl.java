package com.main.datasection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.text.format.Time;

import com.contextsectionInterface.IDataContextCore;
import com.datasectionInterface.IDataQuery;
import com.datasectionInterface.IDataRepository;
import com.eventSectionInterface.IAppX;
import com.main.database.DataBase;

public class DataQueryImpl implements IDataQuery{

	private IAppX AggregateApplicationRepository = DataBase.getAggregateApplicationInstance();
	private IDataRepository DataRepository = DataBase.getDataRepositoryInstance();
	
	@Override
	public Boolean isApplicationAllowedOnContext(String idApplication,
			String idContext) {
		AggregateApplicationRepository.isApplicationAllowedToContext(idApplication, idContext);
		return null;
	}

	@Override
	public Boolean isContextOnDataRepository(String idContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isFunctionOnDataRepository(String idContext,
			String idFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IDataContextCore> getAllDataOfContext(String idContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IDataContextCore> getNDataOfContext(String idContext,
			Integer numberOfDatas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IDataContextCore> getAllDataOfFunction(String idContext,
			String idFunction) {
		//TODO
		return null;
	}

	@Override
	public Set<IDataContextCore> getNDataOfFunction(String idContext,
			String idFunction, Integer numberOfDatas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDataContextCore getLastDataOfFunction(String idContext,
			String idFunction) {
		return DataRepository.getAccessStorage(idContext,idFunction);
	}

	@Override
	public Set<IDataContextCore> getDataOfContextBetween(String idContext,
			Time timeStamp1, Time timeStamp2) {
		List<IDataContextCore> list;
		Set<IDataContextCore> res = new HashSet<IDataContextCore>();
		
		IDataContextCore data;
		
		list = DataRepository.getAccessStorage(timeStamp1.toMillis(true), timeStamp2.toMillis(true));
		
		Iterator<IDataContextCore> it = list.iterator();
		
		while(it.hasNext()){
			data = it.next();
			if(data.getIdContextProvider().equals(idContext)){
				res.add(data);
			}
		}
		
		return res;
	}

	@Override
	public Set<IDataContextCore> getDataOfFunctionBetween(String idContext,
			String idFunction, Time timeStamp1, Time timeStamp2) {
		List<IDataContextCore> list;
		Set<IDataContextCore> res = new HashSet<IDataContextCore>();
		
		IDataContextCore data;
		
		list = DataRepository.getAccessStorage(timeStamp1.toMillis(true), timeStamp2.toMillis(true));
		
		Iterator<IDataContextCore> it = list.iterator();
		
		while(it.hasNext()){
			data = it.next();
			if(data.getIdContextProvider().equals(idContext) && data.getIdFunction().equals(idFunction)){
				res.add(data);
			}
		}
		
		return res;
	}

}
