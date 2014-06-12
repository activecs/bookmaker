package ua.kharkiv.dereza.bookmaker.dto;

/**
 * Data transfer object for DependenceAgeDistanceDAO
 * 
 * @author Eduard
 *
 */
public class DependenceAgeDistanceDTO {
	
	private int id;
	private int age;
	private int distanceId;
	private int chance;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getDistanceId() {
		return distanceId;
	}
	public void setDistanceId(int distanceId) {
		this.distanceId = distanceId;
	}
	public int getChance() {
		return chance;
	}
	public void setChance(int chance) {
		this.chance = chance;
	}
	
	@Override
	public String toString() {
		return "DependenceAgeDistanceDTO [id=" + id + ", age=" + age
				+ ", distanceId=" + distanceId + ", chance=" + chance + "]";
	}
	
}
