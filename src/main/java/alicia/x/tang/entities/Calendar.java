package alicia.x.tang.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import alicia.x.tang.servlets.ServletModule;

/**
 * Calendar info used by the frontend to map fullcalendar event source.
 * @see http://fullcalendar.io/docs/event_data/Event_Source_Object/
 */
public class Calendar {
	private String id;
	private String name;
	private String color;
	private String url;

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return color;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public static Calendar fromGoogleCalendar(com.google.api.services.calendar.model.CalendarListEntry gcal) throws UnsupportedEncodingException {
		Calendar cal = new Calendar();
		cal.setColor(gcal.getBackgroundColor());
		cal.setId(gcal.getId());
		cal.setName(gcal.getSummary());
		cal.setUrl(ServletModule.EVENT
				 + "?cal=" + URLEncoder.encode(gcal.getId(), "UTF-8"));
		return cal;
	}
}
