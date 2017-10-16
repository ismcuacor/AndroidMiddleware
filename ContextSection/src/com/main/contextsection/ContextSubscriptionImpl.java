package com.main.contextsection;

import java.util.UUID;

import android.content.Intent;

import com.contextsectionInterface.IContextSubscription;
import com.main.database.DataBase;

public class ContextSubscriptionImpl implements IContextSubscription {

	private static IContextSubscription instance;

	private ContextRepository ContextRepositoryImpl = DataBase
			.getContextRepositoryInstance();

	private ContextSubscriptionImpl() {
	}

	public static IContextSubscription getInstance() {
		if (instance == null) {
			instance = new ContextSubscriptionImpl();
		}
		return instance;
	}

	// Se encarga de recibir la nueva peticion de registro en el context
	// repository
	// Supuestamente ampliable para introducir en ella la comprobacion de politicas de seguridad
	@Override
	public boolean onNewBundle(ContextInformationCoreImpl contextProvider) {
		registryContextProvider(contextProvider);
		return true;
	}

	// Este método lo hago privado para que solo se pueda usar desde esta clase,
	// porque es logico que
	// solo se pueda encargar esta del registro de los nuevos context providers
	private void registryContextProvider(ContextInformationCoreImpl contextProvider) {
		ContextRepositoryImpl.addAccessStorage(contextProvider);
	}

	// El sistema utiliza este método para generar un identificador único y
	// global asociado al proveedor de contextos.

	// Ismael, esto seguro que te gusta XD, para la generación de una clave
	// única utilizo
	// java.util.UUID que es capaz de proveerme
	// 340.282.366.920.938.463.463.374.607.431.770.000.000 UUIDs
	// para que te hagas una idea de su seguridad:
	/*
	 * Para poner estos números en perspectiva, el riesgo anual de ser golpeado
	 * por un meteorito se estima en una oportunidad en 17 billones, lo que
	 * significa que la probabilidad es de 0.00000000006 (6 × 1013), equivalente
	 * a las probabilidades de crear unas pocas decenas de billones de UUID en
	 * un año y tener un duplicado. En otras palabras, sólo después de generar
	 * UUIDs 1 billón cada segundo durante los próximos 100 años, la
	 * probabilidad de crear un duplicado sería de alrededor del 50%. La
	 * probabilidad de un duplicado sería aproximadamente el 50% si cada persona
	 * en la tierra posee UUIDs 600 millones
	 */

	// por si quieres leer algo mas sobre el tema
	// http://docs.oracle.com/javase/1.5.0/docs/api/java/util/UUID.html
	@Override
	public String getNextContextProviderIdentifier() {
		Long uuid = UUID.randomUUID().getMostSignificantBits();
		return uuid.toString();
	}

	@Override
	public Boolean ping(String idContextProvider) {
		// TODO Esto lo hago cuando Pedrito me pase lo suyo XD
		return null;
	}

}
