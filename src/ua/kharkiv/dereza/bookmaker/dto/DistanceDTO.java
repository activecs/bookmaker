package ua.kharkiv.dereza.bookmaker.dto;

/**
 * Data transfer object for DistanceDAO
 * 
 * @author Eduard
 *
 */
public class DistanceDTO {
	
	private int id;
	private int distance;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return "DistanceDTO [id=" + id + ", distance=" + distance + "]";
	}
	
}
