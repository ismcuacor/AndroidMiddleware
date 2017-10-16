package com.test;


import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class TestContextRepository extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public void main(String[] args) {
		ContentValues values = new ContentValues();
		values.put("subscription", "CP1");
		Uri uri = getContentResolver().insert( Uri.parse("content://com.contextsection.ContextRepository/contextRepository"), values);
		 
		values.clear();
		values.put("subscription", "CP2");
		uri = getContentResolver().insert( Uri.parse("content://com.contextsection.ContextRepository/contextRepository"), values);
		 

	}

}
