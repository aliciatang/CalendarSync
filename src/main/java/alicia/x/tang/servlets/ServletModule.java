package alicia.x.tang.servlets;

import javax.inject.Singleton;

import alicia.x.tang.annotations.BaseUrl;
import alicia.x.tang.annotations.CallBack;

import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.Provides;


public class ServletModule extends com.google.inject.servlet.ServletModule {

	public static final String AUTH = "/auth";
	public static final String CALENDAR = "/calendar";
	public static final String CALLBACK = "/oauth2callback";
	public static final String EVENT = "/event";
	public static final String VIEW = "index.html";

	@Override
	protected void configureServlets() {
		// TODO: figure out why this filter does not work when running on app engine.
		serve(AUTH).with(AuthServlet.class);
		serve(CALLBACK).with(CallBackServlet.class);
	}

	@Provides
	@Singleton
	@BaseUrl
	String provideBaseUrl() {
		return SystemProperty.environment.value() == SystemProperty.Environment.Value.Production
				? "http://norse-coral-95406.appspot.com" : "http://localhost:8080";
	}
	
	@Provides
	@Singleton
	@CallBack
	private String provideCallBackUrl(@BaseUrl String baseUrl) {
		return baseUrl + ServletModule.CALLBACK;
	}
}
