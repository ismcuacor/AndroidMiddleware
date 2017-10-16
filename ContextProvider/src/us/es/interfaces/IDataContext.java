package us.es.interfaces;

import android.os.Parcelable;

public interface IDataContext extends Parcelable {

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
	public abstract Float getAccuracy();

	/* (non-Javadoc)
	 * @see com.contextsection.IDataContext#getValue()
	 */
	public abstract Parcelable getValue();

}