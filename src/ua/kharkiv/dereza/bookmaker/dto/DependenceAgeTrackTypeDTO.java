package ua.kharkiv.dereza.bookmaker.dto;

/**
 * ata transfer object for DependenceAgeTrackTypeDAO
 * 
 * @author Eduard
 *
 */
public class DependenceAgeTrackTypeDTO {
	
	private int id;
	private int age;
	private int trackTypeId;
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
	public int getTrackTypeId() {
		return trackTypeId;
	}
	public void setTrackTypeId(int trackTypeId) {
		this.trackTypeId = trackTypeId;
	}
	public int getChance() {
		return chance;
	}
	public void setChance(int chance) {
		this.chance = chance;
	}
	
	@Override
	public String toString() {
		return "DependenceAgeTrackTypeDTO [id=" + id + ", age=" + age
				+ ", trackTypeId=" + trackTypeId + ", chance=" + chance + "]";
	}
	
}
