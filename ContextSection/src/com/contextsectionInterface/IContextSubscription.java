package com.contextsectionInterface;

import com.main.contextsection.ContextInformationCoreImpl;

public interface IContextSubscription {

	String getNextContextProviderIdentifier();

	Boolean ping(String idContextProvider);

	boolean onNewBundle(ContextInformationCoreImpl contextProvider);
}
