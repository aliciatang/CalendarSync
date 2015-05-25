package alicia.x.tang.entities;

/**
 * Calendar info used by the frontend to display calendar list. 
 */
public class Calendar {
	private String id;
	private String name;
	private String backgroundColor;

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
	public void setBackgroundColor(String color) {
		this.backgroundColor = color;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
}
