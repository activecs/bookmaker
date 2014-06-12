package ua.kharkiv.dereza.bookmaker.gson;

/**
 * Is used when registering 
 * 
 * @author Eduard
 *
 */
public class JsonMessage {
	
	private int errorCode;
	private String message;
	private String errorElement;
	
	public JsonMessage() {
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorElement() {
		return errorElement;
	}
	public void setErrorElement(String errorElement) {
		this.errorElement = errorElement;
	}
	
	@Override
	public String toString() {
		return "Message [errorCode=" + errorCode + ", message="
				+ message + ", errorElement=" + errorElement + "]";
	}
	
}
