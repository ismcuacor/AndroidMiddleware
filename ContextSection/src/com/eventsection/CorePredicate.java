package com.eventsection;

import android.util.Log;

import com.contextsectionInterface.IDataContextCore;
import com.eventSectionInterface.ICorePredicate;

public class CorePredicate {
	public ICorePredicate getCorePredicate(String corePredicateString) {
		Log.w("Core Predicaterl",corePredicateString);
		if (corePredicateString.equals("CorePredicateLessThan")) {
			return new CorePredicateLessThan();
		} else if (corePredicateString.equals("CorePredicateGreatherThan")) {
			return new CorePredicateGreaterThan();
		} else if (corePredicateString.equals("CorePredicateEqualsThan")) {
			return new CorePredicateEqualsThan();
		} else {
			throw new RuntimeException("Core Predicate"+corePredicateString+"no presente");
		}
	}

	private class CorePredicateEqualsThan implements ICorePredicate  {

//		// Compara si el IDataContext.value es mayor que el value
//		private String predicateName;
//		private String predicateDescription;
//
//		public CorePredicateEqualsThan(String pName, String pDescription) {
//			predicateName = pName;
//			predicateDescription = pDescription;
//		}
//
//		@Override
//		public String getName() {
//			return predicateName;
//		}
//
//		@Override
//		public String getDescription() {
//			return predicateDescription;
//		}

		@Override
		public Boolean apply(Object value, IDataContextCore data) {
			Boolean res = false;
			String valueString = (String) value;
			// TODO Comprobar la linea de abajo (Debe comparar objetos, no
			// Strings)
			// Utilizar ContextData
			if (data.getValue().toString().compareTo(valueString.toString()) == 0) {
				res = true;
			}
			return res;
		}

	}

	public class CorePredicateGreaterThan implements ICorePredicate  {

//		// Compara si el IDataContext.value es mayor que el value
//		private String predicateName;
//		private String predicateDescription;
//
//		public CorePredicateGreaterThan(String pName, String pDescription) {
//			predicateName = pName;
//			predicateDescription = pDescription;
//		}
//
//		@Override
//		public String getName() {
//			return predicateName;
//		}
//
//		@Override
//		public String getDescription() {
//			return predicateDescription;
//		}

		@Override
		public Boolean apply(Object value, IDataContextCore data) {
			Boolean res = false;
			String valueString = (String) value;
			// TODO Comprobar la linea de abajo (Debe comparar objetos, no
			// Strings)
			// Utilizar ContextData
			if (data.getValue().toString().compareTo(valueString) > 0) {
				res = true;
			}
			return res;
		}

	}

	public class CorePredicateLessThan implements ICorePredicate  {

		// Compara si el IDataContext.value es menor que el value
//		private String predicateName;
//		private String predicateDescription;
//
//		public CorePredicateLessThan(String pName, String pDescription) {
//			predicateName = pName;
//			predicateDescription = pDescription;
//		}
//
//		@Override
//		public String getName() {
//			return predicateName;
//		}
//
//		@Override
//		public String getDescription() {
//			return predicateDescription;
//		}

		private CorePredicateLessThan (){
			
		}
		
		@Override
		public Boolean apply(Object value, IDataContextCore data) {
			Boolean res = false;
			Object valueString = value;
			// TODO Comprobar la linea de abajo (Debe comparar objetos, no
			// Strings)
			// Utilizar ContextData
			if (data.getValue().toString().compareTo(valueString.toString()) < 0) {
				res = true;
			}
			return res;
		}

	}

}
