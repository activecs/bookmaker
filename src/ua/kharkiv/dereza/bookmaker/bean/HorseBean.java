package ua.kharkiv.dereza.bookmaker.bean;

import java.math.BigDecimal;

/**
 * Bean for HorseDTO
 * 
 * @author Eduard
 *
 */
public class HorseBean implements Comparable<HorseBean>{
	
	private int id;
	private String name;
	private int age;          			// need tranformation
	private String color;
	private int weight;
	private String ownerName; 			// need tranformation
	private String status;				// need tranformation
	private int place;					// need tranformation
	private BigDecimal winCoefficient;  // need tranformation

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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public BigDecimal getWinCoefficient() {
		return winCoefficient;
	}
	public void setWinCoefficient(BigDecimal winCoefficient) {
		this.winCoefficient = winCoefficient;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "HorseBean [id=" + id + ", name=" + name + ", age=" + age
				+ ", color=" + color + ", weight=" + weight + ", ownerName="
				+ ownerName + ", status=" + status + ", place=" + place
				+ ", winCoefficient=" + winCoefficient + "]";
	}
	@Override
	public int compareTo(HorseBean o) {
		HorseBean horseBean = (HorseBean)o;
		int result = place - horseBean.place;
		if (result != 0) return (int)(result/Math.abs(result));
		result = name.compareTo(horseBean.name);
		if (result != 0) return (int)(result/Math.abs(result));
		result = age - horseBean.age;
		if (result != 0) return (int)(result/Math.abs(result));
		result = weight - horseBean.weight;
		if (result != 0) return (int)(result/Math.abs(result));
		result = status.compareTo(horseBean.status);
		if (result != 0) return (int)(result/Math.abs(result));
		result = winCoefficient.compareTo(horseBean.winCoefficient);
		return (result != 0) ? (int)(result/Math.abs(result)) : 0;
	}
}
