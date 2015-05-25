package alicia.x.tang.services;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import alicia.x.tang.entities.Event;
import alicia.x.tang.servlets.EventServlet;
import alicia.x.tang.servlets.ServletModule;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Events;

public class GcalService {
	private final com.google.api.services.calendar.Calendar service;

	public GcalService(com.google.api.services.calendar.Calendar service) {
		this.service = service;
	}

	public List<alicia.x.tang.entities.Calendar> getCalendars() throws IOException {
		CalendarList calendars = service.calendarList().list().execute();
		List<alicia.x.tang.entities.Calendar> ret = new ArrayList<>();
		for(CalendarListEntry item : calendars.getItems()) {
			alicia.x.tang.entities.Calendar cal = new alicia.x.tang.entities.Calendar();
			cal.setColor(item.getBackgroundColor());
			cal.setId(item.getId());
			cal.setName(item.getSummary());
			cal.setUrl(ServletModule.EVENT + "?" + EventServlet.CAL + "=" + URLEncoder.encode(item.getId(), "UTF-8"));
			ret.add(cal);
		}
		return ret;
	}

	public List<Event> getEvents(DateTime min, DateTime max, String cal) throws IOException {
		List<Event> events = new ArrayList<>();
		//TODO: find how to do this in batch.
		Events eventsForCal = service.events()
				.list(cal)
				.setTimeMin(min)
				.setTimeMax(max)
				.execute();
		for(com.google.api.services.calendar.model.Event event : eventsForCal.getItems()){
			Event e = new Event();
			// TODO: handle recurring events.
			e.setId(event.getId());
			e.setTitle(event.getSummary());
			e.setDescription(event.getDescription());
			e.setLocation(event.getLocation());
			// TODO: verify if these works with timezone.
			DateTime start = event.getStart().getDate() == null ? event.getStart().getDateTime() : event.getStart().getDate();
			DateTime end = event.getEnd().getDate() == null ? event.getEnd().getDateTime() : event.getEnd().getDate();
			e.setStart(start.getValue());
			e.setEnd(end.getValue());
			e.setAllDay(start.isDateOnly());
			events.add(e);
		}
		return events;
	}
}
