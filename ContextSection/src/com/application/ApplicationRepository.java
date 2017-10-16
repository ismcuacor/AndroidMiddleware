package com.application;

import java.util.SortedSet;

public interface ApplicationRepository {

	public static final String PROVIDER_NAME = "com.main.contextsection.ApplicationRepository";
	public static final String _ID = "_id";
	public static final String APPLICATION = "application";

	public abstract SortedSet<String> getApplications();

	public abstract boolean isApplicationAllowed(String idApplication);

	public abstract boolean addAllowedApplication(String idApplication, String type, String permission, String description);

	public abstract boolean removeAllowedApplication(String idApplication);

}