package ua.kharkiv.dereza.bookmaker.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main interface for the Command pattern implementation
 * 
 * @author Eduard
 * 
 */
public abstract class Command {

	/**
	 * Execution method for command
	 * 
	 * @param request
	 * @param response
	 * @return Address to go once the command is executed
	 */
	public abstract String execute(HttpServletRequest req,	HttpServletResponse res);

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}
