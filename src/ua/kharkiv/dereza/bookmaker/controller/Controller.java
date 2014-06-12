package ua.kharkiv.dereza.bookmaker.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.command.Command;
import ua.kharkiv.dereza.bookmaker.command.CommandContainer;
import ua.kharkiv.dereza.bookmaker.core.Constants;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static Logger log = Logger.getLogger(Controller.class);

	private static final long serialVersionUID = 1L;

	public Controller() {
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("Controller starts");

		String forward = null;

		// extract command name from the request
		String commandName = request.getParameter("command");
		log.trace("Request parameter: command --> " + commandName);

		try {
			// obtain command object by name
			Command command = CommandContainer.get(commandName);
			log.debug("Obtained command --> " + command);

			// execute command and get forward address
			forward = command.execute(request, response);
			log.trace("Forward address --> " + forward);

		} catch (Exception ex) {
			String message = "Internal error";
			log.error(message, ex);
			forward = Constants.PAGE_ERROR_PAGE;
			request.setAttribute("errorMessage", message);
		}

		// if the forward address is not null go to the address
		if (forward != null) {
			log.debug("Controller finished, now go to forward address --> "
					+ forward);
			RequestDispatcher disp = request.getRequestDispatcher(forward);
			disp.forward(request, response);
		}
		log.debug("Controller finished");
	}
}