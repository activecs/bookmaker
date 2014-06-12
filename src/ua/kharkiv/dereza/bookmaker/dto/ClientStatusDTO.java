package ua.kharkiv.dereza.bookmaker.dto;

/**
 * Data transfer object for ClientStatusDAO
 * 
 * @author Eduard
 *
 */
public class ClientStatusDTO {
	
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
		return "ClientStatusDTO [id=" + id + ", name=" + name + "]";
	}
	
}
