package ua.kharkiv.dereza.bookmaker.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dao.ClientStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;
import ua.kharkiv.dereza.bookmaker.dto.ClientStatusDTO;

/**
 * Activates account after registration.
 * 
 * @author Eduard
 * 
 */
public class AccountActivationCommand extends Command {

	private static final Logger log = Logger
			.getLogger(AccountActivationCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");

		res.setContentType("text/html");

		// message that we return to client
		String message = null;
		String forward = null;

		String login = req.getParameter("login");
		String hash = req.getParameter("hash");
		log.debug("Client with login = " + login + " call for activation");

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);

		// gets dao for client
		ClientDAO clientDAO = mysqlFactory.getClientDAO();

		// gets client by login
		ClientDTO clientDTO = clientDAO.findClientByLogin(login);

		// gets dao for client's statuses
		ClientStatusDAO clientStatusDAO = mysqlFactory.getClientStatusDAO();

		// gets current status id from db
		int statusId = clientDTO.getClientStatusId();
		ClientStatusDTO clientStatusDTO = clientStatusDAO
				.findClientStatusById(statusId);
		String currentClientStatus = clientStatusDTO.getName();
		log.debug("Current client status is " + currentClientStatus);

		// checks current status and hash
		if (clientStatusDTO.getName().equals(
				Constants.CLIENT_STATUSES_NOT_ACTIVE)) {

			// compare hash with md5 hash of user password
			String clientMd5Passsword = clientDTO.getPassword();
			if (clientMd5Passsword.equals(hash)) {
				int statusActiveId = clientStatusDAO.findClientStatusByName(
						Constants.CLIENT_STATUSES_ACTIVE).getId();
				clientDTO.setClientStatusId(statusActiveId);
				clientDAO.updateClient(clientDTO);
				message = "Account activated";
				log.info("Client with login = " + login + " activated");
			}
		} else {
			message = "Account cannot be activated";
			log.debug("Client with login = " + login + " cannot be activated");
		}

		PrintWriter writer = null;
		try {
			writer = res.getWriter();
		} catch (IOException e) {
			forward = Constants.PAGE_ERROR_PAGE;
			message = "Internal error. Cannot get writer";
			req.setAttribute("errorMessage", message);
			req.setAttribute("title", "error");
			log.error(message, e);
			return forward;
		}

		writer.println("<html>");
		writer.println("<head><title>Activation</title></title>");
		writer.println("<body>");
		writer.println("<h1>" + message + "</h1>");
		String url = Constants.APPLICATION_HOST + Constants.APPLICATION_NAME;
		writer.println("<a href=\'" + url + "\'>" + "Click" + "</a>");
		writer.println("</body></html>");

		log.debug("Command finished");

		return forward;
	}

}
