package alicia.x.tang.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

/**
 * Event object map fullcalendar event.
 * @see http://fullcalendar.io/docs/event_data/Event_Object/
 */
public class Event {
	private static final DateFormat DF;

	private String id;
	private String title;
	private String description;
	private String location;
	private String color; // css color value: #eee, rgb(1,2,3)
	private String start; // iso6801 string
	private String end; // iso6801 string
	private boolean allDay = false;
	private String calendar;

	static {
		DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
		DF.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	// TODO: use a builder pattern and make this object immutable.
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	private void setStart(long start) {
		this.start = DF.format(new Date(start));
	}
	public String getStart() {
		return start;
	}
	public void setEnd(long end) {
		this.end = DF.format(new Date(end));
	}
	public String getEnd() {
		return end;
	}
	public void setAllDay(boolean isAllDay) {
		this.allDay = isAllDay;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setCalendar(String calendar) {
		this.calendar= calendar;
	}
	public String getCalendar() {
		return calendar;
	}
	public com.google.api.services.calendar.model.Event toGoogleCalendarEvent() {
		com.google.api.services.calendar.model.Event event = new com.google.api.services.calendar.model.Event();
		event.setStart(new EventDateTime().setDateTime(new DateTime(this.getStart())));
		event.setEnd(new EventDateTime().setDateTime(new DateTime(this.getEnd())));
		event.setSummary(this.getTitle());
		event.setDescription(this.getDescription());
		event.setLocation(this.getLocation());
		return event;
	}
	public static Event fromGoogleEvent(com.google.api.services.calendar.model.Event gEvent) {
		Event event = new Event();
		// TODO: handle recurring events.
		event.setId(gEvent.getId());
		event.setTitle(gEvent.getSummary());
		event.setDescription(gEvent.getDescription());
		event.setLocation(gEvent.getLocation());
		// TODO: verify if these works with timezone.
		DateTime start = gEvent.getStart().getDate() == null ? gEvent.getStart().getDateTime() : gEvent.getStart().getDate();
		DateTime end = gEvent.getEnd().getDate() == null ? gEvent.getEnd().getDateTime() : gEvent.getEnd().getDate();
		event.setStart(start.getValue());
		event.setEnd(end.getValue());
		event.setAllDay(start.isDateOnly());
		return event;
	}
}
