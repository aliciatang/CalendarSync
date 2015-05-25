package alicia.x.tang.entities;

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
}
