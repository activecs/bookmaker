package ua.kharkiv.dereza.bookmaker.bean;

/**
 * Bean for TrialDTO 
 * @author Eduard
 *
 */
public class TrialBean implements Comparable<TrialBean>{

	private int id;
	private int trackId;
	private TrackBean trackBean; // additional row
	private int distance; // need transformation
	private String startTime;
	private String trialStatus; // need transformation

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTrackId() {
		return trackId;
	}

	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}

	public TrackBean getTrackBean() {
		return trackBean;
	}

	public void setTrackBean(TrackBean trackBean) {
		this.trackBean = trackBean;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTrialStatus() {
		return trialStatus;
	}

	public void setTrialStatus(String trialStatus) {
		this.trialStatus = trialStatus;
	}
	
	
	
	@Override
	public String toString() {
		return "TrialBean [id=" + id + ", trackId=" + trackId + ", distanceId="
				+ distance + ", startTime=" + startTime + ", trialStatus="
				+ trialStatus + ", trackBean=" + trackBean + "]";
	}

	@Override
	public int compareTo(TrialBean o) {
		TrialBean trialBean = (TrialBean)o;
		int result = startTime.compareTo(trialBean.startTime);
		if (result != 0) return (int)(result/Math.abs(result));
		result = trackId - trialBean.trackId;
		if (result != 0) return (int)(result/Math.abs(result));
		result = distance - trialBean.distance;
		if (result != 0) return (int)(result/Math.abs(result));		
		result = trackBean.compareTo(trialBean.trackBean);
		if (result != 0) return (int)(result/Math.abs(result));
		result = trialStatus.compareTo(trialBean.trialStatus);
		return (result != 0) ? (int)(result/Math.abs(result)) : 0;
	}
}