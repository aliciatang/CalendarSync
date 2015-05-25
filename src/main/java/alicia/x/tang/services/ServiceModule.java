package alicia.x.tang.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import alicia.x.tang.annotations.CurrentUser;
import alicia.x.tang.entities.User;

import com.google.api.client.auth.oauth2.Credential;
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
	GoogleClientSecrets provideClientSecrets(JsonFactory JsonFactory) throws IOException {
		InputStream in =
				ServiceModule.class.getResourceAsStream(CLIENT_SECRETS);
		return GoogleClientSecrets.load(JsonFactory, new InputStreamReader(in));
	}

	@Provides
	@RequestScoped
	GoogleAuthorizationCodeFlow provideAuthFlow(HttpTransport transport, JsonFactory  jsonFactory, GoogleClientSecrets clientSecrets) {
		return new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory, clientSecrets, SCOPES)
		.setAccessType("offline")
		.build();
	}

	@Provides
	@SessionScoped
	@CurrentUser
	User provideUser() {
		return new User(UUID.randomUUID().getLeastSignificantBits());
	}

	@Provides
	Calendar getCalendar(@CurrentUser User user, HttpTransport transport, JsonFactory jsonFactory, GoogleAuthorizationCodeFlow flow) throws IOException {
		// TODO: load creds from flow instead of session.
		Credential credential = user.getCredential();
		if (credential == null) {
			credential = flow.loadCredential(user.getIdString());
		}
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
