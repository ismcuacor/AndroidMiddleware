package us.es.contextProvider;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMain extends Activity {

	private static TextView viewHora;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_context_provider_timer);
		viewHora = (TextView) findViewById(R.id.datos);
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

			Date d = (Date) b.get("timer");

			if (d == null) {
				Toast.makeText(this, "No se ha enviado la hora",
						Toast.LENGTH_LONG).show();
			} else {
				viewHora.setText(d.toString());
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_context_provider_timer, menu);
		return true;
	}

}
