package alicia.x.tang.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alicia.x.tang.entities.Event;
import alicia.x.tang.services.GcalService;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.servlet.RequestParameters;

@Singleton
@SuppressWarnings("serial")
public class EventServlet extends HttpServlet {
	private static final String MIN = "timeMin";
	private static final String MAX = "timeMax";
	@Inject
	private Provider<GcalService> gcalProvider;
	@Inject
	@RequestParameters
	Provider<Map<String, String[]>> paramsProvider;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		GcalService gcal = gcalProvider.get();
		resp.setContentType("application/json");
		Map<String, String[]> params = paramsProvider.get();
		List<Event> events = gcal.getEvents(getMin(params), getMax(params));
		Gson gson = new Gson();
		// TOOD: figure out how to do this with jersey.
		resp.getWriter().println(gson.toJson(events));
	}

	private static long getMin(Map<String, String[]> params){
		if(params.containsKey(MIN) && params.get(MIN).length > 0) {
			return Long.valueOf(params.get(MIN)[0]) * 1000;
		}
		return getFirstDateOfCurrentMonth();
	}
	private static long getMax(Map<String, String[]> params){
		if(params.containsKey(MAX) && params.get(MAX).length > 0) {
			return Long.valueOf(params.get(MAX)[0]) * 1000;
		}
		return getLastDateOfCurrentMonth();
	}

	private static long getFirstDateOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime().getTime();
	}
	private static long getLastDateOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime().getTime();
	}
}
