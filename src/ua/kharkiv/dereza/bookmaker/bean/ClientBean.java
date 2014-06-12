package ua.kharkiv.dereza.bookmaker.bean;

import java.math.BigDecimal;

/**
 * Bean for ClientDTO
 * 
 * @author Eduard
 *
 */
public class ClientBean {
	
	private int id;
	private String login;
	private String name;
	private String surname;
	private String email;
	private BigDecimal balance;
	private String role;        // need tranformating
	private String locale;      // need tranformating
	private String clientStatus;// need tranformating
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getClientStatus() {
		return clientStatus;
	}
	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}
	@Override
	public String toString() {
		return "ClientBean [id=" + id + ", login=" + login + ", name=" + name
				+ ", surname=" + surname + ", email=" + email
				+ ", balance=" + balance + ", role=" + role + ", locale="
				+ locale + ", clientStatus=" + clientStatus + "]";
	}
	
}
