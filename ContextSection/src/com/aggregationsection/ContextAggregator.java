package com.aggregationsection;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.util.Log;

import com.aggregationsectionInterface.IContextAggregator;
import com.aggregationsectionInterface.IAggregateCore;
import com.contextsectionInterface.IDataContextCore;
import com.eventsection.EventProvider;

@SuppressLint("UseSparseArrays")
public class ContextAggregator implements IContextAggregator {
	private static ContextAggregator instance = null;
	private static EventProvider eventProviderInstance = null;
	private static Integer instances = 0;
	/*
	 * The attribute 'activeAggreggations' is used for storing the whole of the
	 * last active aggregates. The module 'Aggreggator Context' is used to refer
	 * to the currently active aggregates, quickly and easily, using a type
	 * attribute key-value pair, where the key matches the identifier and the
	 * value added to the state thereof.
	 */
	Map<Integer, Boolean> activeAggregations = new HashMap<Integer, Boolean>();

	/*
	 * It is necessary to keep a local variable with a copy of the values to
	 * re-evaluate changes when it is needed
	 */
	Map<Integer, IAggregateCore> aggregates = new HashMap<Integer, IAggregateCore>();;

	public ContextAggregator (){
		Log.w("Constructor", "");
		eventProviderInstance = EventProvider.getInstance();
	}
	
	public static ContextAggregator getInstance(){
		if (instances == 0){
			instance = new ContextAggregator();
			instances++;
		}
		return instance;
	}
	
	@Override
	public void onDataRepositoryChange(IDataContextCore data, String modification) {
		//TODO: Actualizar data
		if (modification.equals("DELETE")){
			Log.w("Delete", "Delete");
			eventProviderInstance.onNewAggregationActivation(0);
		}else if (modification.equals("UPDATE")){
			Log.w("Update", data.toString());
			Map<Integer, IAggregateCore> affectedAggregates = getAffectedAggregates(data);
			checkAggregates(affectedAggregates);
		}
	}

	@Override
	public void checkAggregates(final Map<Integer, IAggregateCore> affectedAggregates) {
		for (final Integer idAggregate:affectedAggregates.keySet()){
			//TODO No lo termino de ver, usar threads en un for
			/* Use of threads in order to parallelize evaluation*/
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Boolean value = checkAggregate(idAggregate, affectedAggregates.get(idAggregate));
					if (activeAggregations.get(idAggregate).equals(true) && value.equals(false)){
						eventProviderInstance.onNewAggregationActivation(idAggregate);
						activeAggregations.put(idAggregate, value);
					}else if (activeAggregations.get(idAggregate).equals(false) && value.equals(true)){
						eventProviderInstance.onNewDefuseAggregation(idAggregate);
						activeAggregations.put(idAggregate, value);						
					}
				}
			}).start();
		}
	}

	@Override
	public Boolean checkAggregate(Integer idAggregate, IAggregateCore aggregate) {
		Boolean value = aggregate.evaluate();
		return value.equals(activeAggregations.get(idAggregate));
	}

	@Override
	public Map<Integer, IAggregateCore> getAffectedAggregates(IDataContextCore data) {
		Map<Integer,IAggregateCore> affectedAggregates = new HashMap<Integer, IAggregateCore>();
		Log.w("aggregates", "Entrar");

		for (Integer idAggregate:aggregates.keySet()){
			Log.w("aggregates", idAggregate.toString());
			if((aggregates.get(idAggregate)).isAggregateAffected(data)){
				affectedAggregates.put(idAggregate, aggregates.get(idAggregate));
			}
		}
		return affectedAggregates;
	}
}
