package alicia.x.tang.servlets;


public class ServletModule extends com.google.inject.servlet.ServletModule {
//	public static final String BASE_URL = "http://norse-coral-95406.appspot.com";
	public static final String BASE_URL = "http://localhost:8080";
	
	public static final String AUTH = "/auth";
	public static final String CALENDAR = "/calendar";
	public static final String CALLBACK = "/oauth2callback";
	public static final String EVENT = "/event";
	public static final String VIEW = "index.html";

	@Override
	protected void configureServlets() {
		// TODO: figure out why this filter does not work when running on app engine.
        //filter(EVENT).through(UserAuthFilter.class);
        //filter(CALENDAR).through(UserAuthFilter.class);

		serve(AUTH).with(AuthServlet.class);
		serve(CALLBACK).with(CallBackServlet.class);
		serve(CALENDAR).with(CalendarServlet.class);
		//serve(EVENT).with(EventServlet.class);
	}
}
