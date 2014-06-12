package ua.kharkiv.dereza.bookmaker.dto;

/**
 * Data transfer object for DependenceAgeCoverDAO
 * 
 * @author Eduard
 *
 */
public class DependenceAgeCoverDTO {
	
	private int id;
	private int age;
	private int coverId;
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
	public int getCoverId() {
		return coverId;
	}
	public void setCoverId(int coverId) {
		this.coverId = coverId;
	}
	public int getChance() {
		return chance;
	}
	public void setChance(int chance) {
		this.chance = chance;
	}
	
	@Override
	public String toString() {
		return "DependenceAgeCoverDTO [id=" + id + ", age=" + age
				+ ", coverId=" + coverId + ", chance=" + chance + "]";
	}
	
}
