package ua.kharkiv.dereza.bookmaker.dto;

import java.util.Date;

/**
 * Data transfer object for trial's
 * 
 * @author Eduard
 *
 */
public class TrialDTO {
	
	private int id;
	private int trackId;
	private int distanceId;
	private Date startTime;
	private int trialStatusId;
	
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
	public int getDistanceId() {
		return distanceId;
	}
	public void setDistanceId(int distanceId) {
		this.distanceId = distanceId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public int getTrialStatusId() {
		return trialStatusId;
	}
	public void setTrialStatusId(int trialStatusId) {
		this.trialStatusId = trialStatusId;
	}
	
	@Override
	public String toString() {
		return "TrialDTO [id=" + id + ", trackId=" + trackId + ", distanceId="
				+ distanceId + ", startTime=" + startTime + ", trialStatusId="
				+ trialStatusId + "]";
	}
	
}
