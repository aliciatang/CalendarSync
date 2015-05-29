package alicia.x.tang.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alicia.x.tang.annotations.CallBack;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
@SuppressWarnings("serial")
public class AuthServlet extends HttpServlet {
	@Inject
	@CallBack
	private String callBackUrl;
	@Inject
	private Provider<GoogleAuthorizationCodeFlow> flowProvider;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		GoogleAuthorizationCodeFlow flow = flowProvider.get();
		//TODO: setState for security.
		String url = flow.newAuthorizationUrl()
				.setRedirectUri(callBackUrl)
				.build();
		resp.sendRedirect(url);
	}
}
