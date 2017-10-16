package com.datasectionInterface;

import java.util.Set;

import android.text.format.Time;

import com.contextsectionInterface.IDataContextCore;

public interface IDataQuery {
	Boolean isApplicationAllowedOnContext(String idApplicationBundle,
			String idContext);

	Boolean isContextOnDataRepository(String idContext);

	Boolean isFunctionOnDataRepository(String idContext, String idFunction);

	Set<IDataContextCore> getAllDataOfContext(String idContext);

	Set<IDataContextCore> getNDataOfContext(String idContext, Integer numberOfDatas);

	Set<IDataContextCore> getAllDataOfFunction(String idContext, String idFunction);

	Set<IDataContextCore> getNDataOfFunction(String idContext, String idFunction,
			Integer numberOfDatas);

	IDataContextCore getLastDataOfFunction(String idContext, String idFunction);

	Set<IDataContextCore> getDataOfContextBetween(String idContext,
			Time timeStamp1, Time timeStamp2);

	Set<IDataContextCore> getDataOfFunctionBetween(String idContext,
			String idFunction, Time timeStamp1, Time timeStamp2);
}
