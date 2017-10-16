package com.contextproviderlocation;

import com.contextproviderlocation.R;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMain extends Activity {

	private TextView viewLocation;
	private static TextView viewAltitud;
	private static TextView viewLocation2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_context_provider_location);
		viewLocation = (TextView) findViewById(R.id.viewLocation);
		viewAltitud = (TextView) findViewById(R.id.altitud);
		viewLocation2 = (TextView) findViewById(R.id.longitud);

		Bundle b = getIntent().getExtras();
		if (b == null) {
			try {
				Toast.makeText(this, "Iniciando el servicio...",
						Toast.LENGTH_LONG).show();
				Intent servicio = new Intent(this, Main.class);
				if (startService(servicio) == null) {
					Toast.makeText(this, "No se ha podido iniciar el servicio",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(this, "Servicio iniciado correctamente",
							Toast.LENGTH_LONG).show();
				}

			} catch (Exception e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		} else {

			Location loc = (Location) b
					.get(android.location.LocationManager.KEY_LOCATION_CHANGED);

			if (loc == null) {
				Toast.makeText(
						this,
						"Localización no encontrada. Puede que el GPS esté desactivado.",
						Toast.LENGTH_LONG).show();

			} else {
				Double altitud = loc.getAltitude();
				viewAltitud.setText(altitud.toString());
				Double longitude = loc.getLongitude();
				Double latitude = loc.getLatitude();
				viewLocation2.setText(latitude.toString() + ", "
						+ longitude.toString());
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.context_provider_location, menu);
		return true;
	}

	public static TextView getViewLocation() {
		return viewLocation2;
	}

	public static TextView getViewAltitude() {
		return viewAltitud;
	}

}
