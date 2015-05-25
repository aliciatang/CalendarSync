package alicia.x.tang.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
@SuppressWarnings("serial")
public class AuthServlet extends HttpServlet {
	// TODO: make this url works on app engine.
	private static final String CALL_BACK_URL = ServletModule.BASE_URL+ ServletModule.CALLBACK;
	@Inject
	private Provider<GoogleAuthorizationCodeFlow> flowProvider;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws IOException {
		GoogleAuthorizationCodeFlow flow = flowProvider.get();
		//TODO: setState for security.
		String url = flow.newAuthorizationUrl()
		    .setRedirectUri(CALL_BACK_URL)
		    .build();
		resp.sendRedirect(url);
	}
}
