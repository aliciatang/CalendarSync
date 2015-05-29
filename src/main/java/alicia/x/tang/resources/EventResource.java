package alicia.x.tang.resources;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import alicia.x.tang.entities.Event;
import alicia.x.tang.services.GcalService;

import com.google.api.client.util.DateTime;
import com.google.inject.Provider;

@Path("/event")
public class EventResource {

	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger LOGGER = Logger.getLogger(EventResource.class.getName());

	@Inject
	private Provider<GcalService> gcalProvider;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object get(@QueryParam("cal") String cal, @QueryParam("start") String start, @QueryParam("end") String end ) throws IOException {
		GcalService gcal = gcalProvider.get();
		return gcal.getEvents(getStart(start), getEnd(end), cal);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object create(@QueryParam("cal") String cal, Event event) throws IOException {
		GcalService gcal = gcalProvider.get();
		return gcal.createEvent(event, cal);
	}

	private static DateTime getStart(@Nullable String start) {
		try {
			return new DateTime(DF.parse(start));
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Failed to parse start time:" + start);
			return getFirstDateOfCurrentMonth();
		}
	}
	private static DateTime getEnd(@Nullable String end) {
		try {
			return new DateTime(DF.parse(end));
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Failed to parse end time:" + end);
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
