package com.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.main.core.R;



public class ContextSection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_context_section);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.context_section, menu);
		return true;
	}

}
