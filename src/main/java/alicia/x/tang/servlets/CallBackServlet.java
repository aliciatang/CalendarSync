package alicia.x.tang.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alicia.x.tang.annotations.CurrentUser;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.servlet.RequestParameters;

@Singleton
@SuppressWarnings("serial")
public class CallBackServlet extends HttpServlet {
	// TODO: make this url works on app engine.
	private static final String CALL_BACK_URL = ServletModule.BASE_URL + ServletModule.CALLBACK;
	private static final String CODE = "code";
	@Inject
	private Provider<GoogleAuthorizationCodeFlow> flowProvider;

	@Inject @RequestParameters
	private Provider<Map<String, String[]>> reqParamsProvider;
	@Inject @CurrentUser
	private Provider<String> currentUserProvider;


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		GoogleAuthorizationCodeFlow flow = flowProvider.get();
		String code = reqParamsProvider.get().get(CODE)[0];
		GoogleAuthorizationCodeTokenRequest request = flow.newTokenRequest(code);
		request.setRedirectUri(CALL_BACK_URL);
		GoogleTokenResponse response = request.execute();
		String currentUser = currentUserProvider.get(); 
		flow.createAndStoreCredential(response, currentUser);
		resp.setContentType("application/json");
		resp.getWriter().println("{seccuss: true}");
	}

}
