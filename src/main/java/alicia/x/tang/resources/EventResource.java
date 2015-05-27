package alicia.x.tang.resources;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.api.client.util.DateTime;
import com.google.gson.Gson;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestParameters;

import alicia.x.tang.entities.Event;
import alicia.x.tang.services.GcalService;

@Path("/event")
public class EventResource {

	private static final String MIN = "start";
	private static final String MAX = "end";
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	
	@Inject
	private Provider<GcalService> gcalProvider;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object get(@QueryParam("cal") String cal, @QueryParam("start") String start, @QueryParam("cal") String end ) throws IOException {
		GcalService gcal = gcalProvider.get();
		return gcal.getEvents(getStart(start), getEnd(end), cal);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object create(String contact) {
		return contact;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Object json(@PathParam("id") Long id) {
		return id;
	}
	
	private static DateTime getStart(@Nullable String start) {
		try {
			return new DateTime(DF.parse(start));
		} catch (Exception e) {
			return getFirstDateOfCurrentMonth();
		}
	}
	private static DateTime getEnd(@Nullable String end) {
		try {
			return new DateTime(end);
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
