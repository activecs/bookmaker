package ua.kharkiv.dereza.bookmaker.dto;

/**
 * Data transfer object for HorseDAO
 * 
 * @author Eduard
 *
 */
public class HorseDTO {
	
	private int id;
	private String name;
	private int birthYear;
	private String color;
	private int weight;
	private int ownerId;
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
	public int getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	@Override
	public String toString() {
		return "HorseDTO [id=" + id + ", name=" + name+ ", birthYear=" + birthYear + ", color=" + color + ", weight="
				+ weight + ", ownerId=" + ownerId + "]";
	}
}
