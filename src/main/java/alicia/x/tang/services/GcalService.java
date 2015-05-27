package alicia.x.tang.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alicia.x.tang.entities.Calendar;
import alicia.x.tang.entities.Event;

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
			ret.add(Calendar.fromGoogleCalendar(item));
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
			events.add(Event.fromGoogleEvent(event));
		}
		return events;
	}
	
	public Event createEvent(Event event, String cal) throws IOException {
		com.google.api.services.calendar.model.Event createdEvent =
				service.events().insert(cal, event.toGoogleCalendarEvent()).execute();
		event.setId(createdEvent.getId());
		return event;
	}
}
