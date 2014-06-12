package ua.kharkiv.dereza.bookmaker.dto;

import java.math.BigDecimal;

/**
 * Data transfer object for client's bets
 * 
 * @author Eduard
 *
 */
public class BetDTO {
	
	private int id;
	
	private int clientId;
	
	private int trialHoseId;
	
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
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "BetDTO [id=" + id + ", clientId=" + clientId + ", trialHoseId="
				+ trialHoseId + ", value=" + value + "]";
	}
}
