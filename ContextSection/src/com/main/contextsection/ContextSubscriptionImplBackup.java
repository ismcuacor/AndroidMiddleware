//package com.main.contextsection;
//
//import java.util.UUID;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//
//import android.util.Log;
//
//import com.contextsectionInterface.IContextInformation;
//import com.contextsectionInterface.IContextInformationCore;
//import com.contextsectionInterface.IContextSubscription;
//import com.main.database.DataBase;
//
//public class ContextSubscriptionImplBackup extends Activity implements
//		IContextSubscription {
//
//	private static IContextSubscription instance;
//
//	private ContextRepository ContextRepositoryImpl = DataBase
//			.getContextRepositoryInstance();
//
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		Bundle capture = getIntent().getExtras();
//		
//		if (capture != null) {
//			String idPaquete = capture.getString("envio");
//
//			IContextInformation contextAux = capture
//					.getParcelable("contextInformation");
//			IContextInformationCore context = new ContextInformationCoreImpl(
//					idPaquete, contextAux);
//
//			ContextRepositoryImpl.addAccessStorage(context);
//
//			Intent intent = new Intent();
//			intent.setClassName("com.main.core",
//					"com.main.contextsection.ContextUpdaterImpl");
//			intent.addCategory("android.intent.category.LAUNCHER");
//			intent.putExtra("subscription", idPaquete);
//			startActivity(intent);
//
//		} else
//			Log.w("Prueba", "No ha entrado en el if");
//	}
//
//	public static IContextSubscription getInstance() {
//		if (instance == null) {
//			instance = new ContextSubscriptionImplBackup();
//		}
//		return instance;
//	}
//
//	// Se encarga de recibir la nueva peticion de registro en el context
//	// repository
//	@Override
//	public void onNewBundle(ContextInformationCoreImpl contextProvider) {
//		// Map<String, String> receive = new HashMap<String, String>();
//		// ISender send = new SenderImpl();
//		// receive = send.receive();
//		// if (receive.containsKey(contextProvider.getName())) {
//		registryContextProvider(contextProvider);
//		// }
//
//	}
//
//	// Este método lo hago privado para que solo se pueda usar desde esta clase,
//	// porque es logico que
//	// solo se pueda encargar esta del registro de los nuevos context providers
//	private void registryContextProvider(
//			ContextInformationCoreImpl contextProvider) {
//		ContextRepositoryImpl.addAccessStorage(contextProvider);
//
//	}
//
//	// El sistema utiliza este método para generar un identificador único y
//	// global asociado al proveedor de contextos.
//
//	// Ismael, esto seguro que te gusta XD, para la generación de una clave
//	// única utilizo
//	// java.util.UUID que es capaz de proveerme
//	// 340.282.366.920.938.463.463.374.607.431.770.000.000 UUIDs
//	// para que te hagas una idea de su seguridad:
//	/*
//	 * Para poner estos números en perspectiva, el riesgo anual de ser golpeado
//	 * por un meteorito se estima en una oportunidad en 17 billones, lo que
//	 * significa que la probabilidad es de 0.00000000006 (6 × 1013), equivalente
//	 * a las probabilidades de crear unas pocas decenas de billones de UUID en
//	 * un año y tener un duplicado. En otras palabras, sólo después de generar
//	 * UUIDs 1 billón cada segundo durante los próximos 100 años, la
//	 * probabilidad de crear un duplicado sería de alrededor del 50%. La
//	 * probabilidad de un duplicado sería aproximadamente el 50% si cada persona
//	 * en la tierra posee UUIDs 600 millones
//	 */
//
//	// por si quieres leer algo mas sobre el tema
//	// http://docs.oracle.com/javase/1.5.0/docs/api/java/util/UUID.html
//	@Override
//	public String getNextContextProviderIdentifier() {
//		Long uuid = UUID.randomUUID().getMostSignificantBits();
//		return uuid.toString();
//	}
//
//	@Override
//	public Boolean ping(String idContextProvider) {
//		// TODO Esto lo hago cuando Pedrito me pase lo suyo XD
//		return null;
//	}
//
//}
