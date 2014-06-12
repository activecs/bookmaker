package ua.kharkiv.dereza.bookmaker.dto;

import java.math.BigDecimal;

/**
 * Data transfer object for TrialHorseDAO
 * 
 * @author Eduard
 *
 */
public class TrialHorseDTO {
	
	private int id;
	private int trialId;
	private int horseId;
	private int place;
	private BigDecimal winCoefficient;
	private int horseStatusId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTrialId() {
		return trialId;
	}
	public void setTrialId(int trialId) {
		this.trialId = trialId;
	}
	public int getHorseId() {
		return horseId;
	}
	public void setHorseId(int horseId) {
		this.horseId = horseId;
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
	public int getHorseStatusId() {
		return horseStatusId;
	}
	public void setHorseStatusId(int horseStatusId) {
		this.horseStatusId = horseStatusId;
	}
	
	@Override
	public String toString() {
		return "TrialHorseDTO [id=" + id + ", trialId=" + trialId
				+ ", horseId=" + horseId + ", place=" + place
				+ ", winCoefficient=" + winCoefficient + ", horseStatusId="
				+ horseStatusId + "]";
	}
		
}
