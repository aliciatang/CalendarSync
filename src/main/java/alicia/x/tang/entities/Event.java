package alicia.x.tang.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

	static {
		DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
		DF.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	// TODO: use a builder pattern and make this object immutable.
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setStart(long start) {
		this.start = DF.format(new Date(start));
	}
	public void setEnd(long end) {
		this.end = DF.format(new Date(end));
	}
	public void setAllDay(boolean isAllDay) {
		this.allDay = isAllDay;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
