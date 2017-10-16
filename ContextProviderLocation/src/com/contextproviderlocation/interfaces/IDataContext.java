package com.contextproviderlocation.interfaces;

import java.io.Serializable;

public interface IDataContext extends Serializable {

	/* (non-Javadoc)
	 * @see com.contextsection.IDataContext#getIdFunction()
	 */
	public abstract String getIdFunction();

	/* (non-Javadoc)
	 * @see com.contextsection.IDataContext#getTimeStamp()
	 */
	public abstract Long getTimeStamp();

	/* (non-Javadoc)
	 * @see com.contextsection.IDataContext#getAccuracy()
	 */
	public abstract Integer getAccuracy();

	/* (non-Javadoc)
	 * @see com.contextsection.IDataContext#getValue()
	 */
	public abstract String getValue();

}