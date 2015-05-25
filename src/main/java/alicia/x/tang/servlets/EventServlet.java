package alicia.x.tang.servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alicia.x.tang.entities.Event;
import alicia.x.tang.services.GcalService;

import com.google.api.client.util.DateTime;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.servlet.RequestParameters;

@Singleton
@SuppressWarnings("serial")
public class EventServlet extends HttpServlet {
	private static final String MIN = "start";
	private static final String MAX = "end";
	public static final String CAL = "cal";
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	
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
		List<Event> events = gcal.getEvents(getStart(params), getEnd(params), getCal(params));
		Gson gson = new Gson();
		// TOOD: figure out how to do this with jersey.
		resp.getWriter().println(gson.toJson(events));
	}

	private static String getCal(Map<String, String[]> params) {
		if (params.containsKey(CAL) && params.get(CAL).length > 0) {
			return params.get(CAL)[0];
		}
		throw new IllegalArgumentException("No calendar specified");
	}

	private static DateTime getStart(Map<String, String[]> params) {
		try {
			return new DateTime(DF.parse(params.get(MIN)[0]));
		} catch (Exception e) {
			return getFirstDateOfCurrentMonth();
		}
	}
	private static DateTime getEnd(Map<String, String[]> params) {
		try {
			return new DateTime(DF.parse(params.get(MAX)[0]));
		} catch (Exception e) {
			return getLastDateOfCurrentMonth();
		}
	}

	private static DateTime getFirstDateOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
		return new DateTime(cal.getTime());
	}
	private static DateTime getLastDateOfCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
		return new DateTime(cal.getTime());
	}
}
