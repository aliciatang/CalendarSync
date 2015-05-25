package alicia.x.tang.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import alicia.x.tang.annotations.CurrentUser;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;


public class ServiceModule extends AbstractModule {
	private static final String APPLICATION_NAME = "CalendarSync";
	private static final String CLIENT_SECRETS = "/client_secret.json";
	private static final List<String> SCOPES =
			Arrays.asList(CalendarScopes.CALENDAR_READONLY);
	@Override
	protected void configure() {
		// TODO Auto-generated method stub
	}

	@Provides
	@Singleton
	HttpTransport provideHttpTransport() throws GeneralSecurityException, IOException {		
		return GoogleNetHttpTransport.newTrustedTransport();
	}

	@Provides
	@Singleton
	JsonFactory provideJsonFactory() {
		return JacksonFactory.getDefaultInstance();
	}
	
	@Provides
	@Singleton
	AppEngineDataStoreFactory provideDataStoreFactory() {
		return AppEngineDataStoreFactory.getDefaultInstance();
	}

	@Provides
	@Singleton
	GoogleClientSecrets provideClientSecrets(JsonFactory JsonFactory) throws IOException {
		InputStream in =
				ServiceModule.class.getResourceAsStream(CLIENT_SECRETS);
		return GoogleClientSecrets.load(JsonFactory, new InputStreamReader(in));
	}

	@Provides
	@RequestScoped
	GoogleAuthorizationCodeFlow provideAuthFlow(HttpTransport transport, JsonFactory  jsonFactory, GoogleClientSecrets clientSecrets, AppEngineDataStoreFactory dataStore) throws IOException {
		return new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory, clientSecrets, SCOPES)
		.setAccessType("offline")
		.setDataStoreFactory(dataStore)
		.build();
	}

	@Provides
	@SessionScoped
	@CurrentUser
	String provideUser() {
		return UUID.randomUUID().toString();
	}

	@Provides
	Calendar getCalendar(@CurrentUser String user, HttpTransport transport, JsonFactory jsonFactory, GoogleAuthorizationCodeFlow flow) throws IOException {
		Credential credential = flow.loadCredential(user);
		if (credential == null) {
			throw new IllegalStateException("User have not be autherized yet.");
		}
		return new Calendar.Builder(transport, jsonFactory, credential)
		.setApplicationName(APPLICATION_NAME)
		.build();
	}

	@Provides
	GcalService getGcal(Calendar calendar) {
		return new GcalService(calendar);
	}

}
