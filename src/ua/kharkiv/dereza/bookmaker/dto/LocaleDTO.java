package ua.kharkiv.dereza.bookmaker.dto;

/**
 * Data transfer object for client's locale
 * 
 * @author Eduard
 *
 */
public class LocaleDTO {
	
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "LocaleDTO [id=" + id + ", name=" + name + "]";
	}
	
}
