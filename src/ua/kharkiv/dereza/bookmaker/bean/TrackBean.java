package ua.kharkiv.dereza.bookmaker.bean;

/**
 * Bean for TrackDTO
 * @author Eduard
 *
 */
public class TrackBean implements Comparable<TrackBean>{
	
	private int id;	
	private String name;
	private String country;
	private String cover;		//need transformation
	private String trackType;	//need transformation
	
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
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getTrackType() {
		return trackType;
	}
	public void setTrackType(String trackType) {
		this.trackType = trackType;
	}
	
	@Override
	public String toString() {
		return "TrackBean [id=" + id + ", name=" + name + ", country="
				+ country + ", cover=" + cover + ", trackType=" + trackType
				+ "]";
	}
	
	@Override
	public int compareTo(TrackBean o) {
		TrackBean trackBean = (TrackBean)o;
		int result = name.compareTo(trackBean.name);
		if (result != 0) return (int)(result/Math.abs(result));
		result = country.compareTo(trackBean.country);
		if (result != 0) return (int)(result/Math.abs(result));
		result = cover.compareTo(trackBean.cover);
		if (result != 0) return (int)(result/Math.abs(result));
		result = trackType.compareTo(trackBean.trackType);
		return (result != 0) ? (int)(result/Math.abs(result)) : 0;
	}
	
}
