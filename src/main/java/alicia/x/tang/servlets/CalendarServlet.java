package alicia.x.tang.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alicia.x.tang.entities.Calendar;
import alicia.x.tang.services.GcalService;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
@SuppressWarnings("serial")
public class CalendarServlet extends HttpServlet {

	@Inject
	private Provider<GcalService> gcalProvider;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		GcalService gcal = gcalProvider.get();
		resp.setContentType("application/json");
		List<Calendar> calendars = gcal.getCalendars();
		resp.getWriter().println(new Gson().toJson(calendars));
	}

}
