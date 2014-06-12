package ua.kharkiv.dereza.bookmaker.dto;

import java.math.BigDecimal;

/**
 * Data transfer object for ClientDAO
 * 
 * @author Eduard
 *
 */
public class ClientDTO {
	
	private int id;
	private String login;
	private String password;
	private String name;
	private String surname;
	private String email;
	private BigDecimal balance;
	private int roleId;
	private int localeId;
	private int clientStatusId;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getLocaleId() {
		return localeId;
	}
	public void setLocaleId(int localeId) {
		this.localeId = localeId;
	}
	public int getClientStatusId() {
		return clientStatusId;
	}
	public void setClientStatusId(int clientStatusId) {
		this.clientStatusId = clientStatusId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "ClientDTO [id=" + id + ", login=" + login + ", password="
				+ password + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", balance=" + balance + ", roleId="
				+ roleId + ", localeId=" + localeId + ", clientStatusId="
				+ clientStatusId + "]";
	}
}