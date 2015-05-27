package alicia.x.tang.resources;


import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import alicia.x.tang.services.GcalService;

import com.google.inject.Provider;

@Path("/calendar")
public class CalendarResource {

	@Inject
	private Provider<GcalService> gcalProvider;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object get() throws IOException {
		GcalService gcal = gcalProvider.get();
		return gcal.getCalendars();
	}
}
