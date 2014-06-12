package ua.kharkiv.dereza.bookmaker.dto;

/**
 * Data transfer object for tracks
 * 
 * @author Eduard
 *
 */
public class TrackDTO {
	
	private int id;
	private String name;
	private String country;
	private int coverId;
	private int trackTypeId;
	
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getCoverId() {
		return coverId;
	}
	public void setCoverId(int coverId) {
		this.coverId = coverId;
	}
	public int getTrackTypeId() {
		return trackTypeId;
	}
	public void setTrackTypeId(int trackTypeId) {
		this.trackTypeId = trackTypeId;
	}
	
	@Override
	public String toString() {
		return "TrackDTO [id=" + id + ", name=" + name + ", country=" + country
				+ ", coverId=" + coverId + ", trackTypeId=" + trackTypeId + "]";
	}
	
}
