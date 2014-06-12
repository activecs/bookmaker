package ua.kharkiv.dereza.bookmaker.bean;

import java.math.BigDecimal;

/**
 * Bean for BetDTO
 * 
 * @author Eduard
 *
 */
public class BetBean implements Comparable<BetBean>{

private int id;
	
	private int clientId;
	
	private int trialHoseId;
	
	private TrialBean trial;
	
	private HorseBean horse;
	
	private BigDecimal value;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getTrialHoseId() {
		return trialHoseId;
	}

	public void setTrialHoseId(int trialHoseId) {
		this.trialHoseId = trialHoseId;
	}

	public TrialBean getTrial() {
		return trial;
	}

	public void setTrial(TrialBean trial) {
		this.trial = trial;
	}

	public HorseBean getHorse() {
		return horse;
	}

	public void setHorse(HorseBean horse) {
		this.horse = horse;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BetBean [id=" + id + ", clientId=" + clientId
				+ ", trialHoseId=" + trialHoseId + ", trial=" + trial
				+ ", horse=" + horse + ", value=" + value + "]";
	}

	@Override
	public int compareTo(BetBean o) {
		BetBean betbean = (BetBean)o;
		int result = trial.getStartTime().compareTo(betbean.getTrial().getStartTime());
		if (result != 0) return -(int)(result/Math.abs(result));
		result = trial.getTrackBean().getName().compareTo(betbean.getTrial().getTrackBean().getName());
		if (result != 0) return -(int)(result/Math.abs(result));
		result = horse.getName().compareTo(betbean.getHorse().getName());
		return (result != 0) ? -(int)(result/Math.abs(result)) : 0;
	}
}
