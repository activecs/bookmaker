package ua.kharkiv.dereza.bookmaker.command;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.gson.*;
import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.core.EmailSender;
import ua.kharkiv.dereza.bookmaker.core.PasswordEncryptor;
import ua.kharkiv.dereza.bookmaker.core.VelocityParser;
import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;

import com.google.gson.Gson;

/**
 * Registers new client.
 * 
 * @author Eduard
 * 
 */
public class RegistrationCommand extends Command {

	private static final Logger log = Logger
			.getLogger(RegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");

		res.setContentType("application/json");

		// desired login
		String login = req.getParameter("login");

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);

		// creates DAO for client
		ClientDAO clientDAO = mysqlFactory.getClientDAO();
		
		String forward = null;
		String message = null;
		String errorElement = null;
		JsonMessage jsonMessage = new JsonMessage();
		String json = null;

		// create instance of json converter
		Gson gson = new Gson();

		// Checks whether a given login is free
		ClientDTO clientDTO = clientDAO.findClientByLogin(login);
		log.trace("Found in bd client --> " + clientDTO);
		if (clientDTO != null) {
			message = "login is busy";
			errorElement = "login";
			jsonMessage.setErrorCode(1);
			jsonMessage.setErrorElement(errorElement);
			jsonMessage.setMessage(message);
			log.debug("Can't register account -> " + message);

		} else {

			// create new client
			clientDTO = new ClientDTO();
			clientDTO.setLogin(login);
			clientDTO.setName(req.getParameter("name"));
			clientDTO.setSurname(req.getParameter("surname"));
			clientDTO.setEmail(req.getParameter("email"));

			// generate md5 from password
			String password = req.getParameter("password");
			PasswordEncryptor pass = new PasswordEncryptor();
			String ecryptedPssword = pass.encode(password);
			clientDTO.setPassword(ecryptedPssword);

			// write to db information about new client
			try{
				clientDAO.createClient(clientDTO);
				log.info("Created new client -> " + clientDTO);
				jsonMessage.setErrorCode(0);
				message = "Please check you email. You have to activate your account.";
			}catch(RuntimeException ex){
				log.info("Cannot created new client -> " + clientDTO);
				jsonMessage.setErrorCode(1);
				message = "Cannot created new client. Please try again later.";
			}

			jsonMessage.setMessage(message);
			
			// creates hashmap which will be used for creating emails
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put("login", clientDTO.getLogin());
			parameters.put("email", clientDTO.getEmail());
			parameters.put("hash", clientDTO.getPassword());
			parameters.put("path", Constants.APPLICATION_HOST + Constants.APPLICATION_NAME + Constants.APPLICATION_CONTROLLER);
			log.trace("Data for template --> " + parameters);
			
			try {
				// creates messages for client and admin
				VelocityParser velocity = new VelocityParser();
				String emailForClient = velocity.getMessage(
						Constants.VELOCITY_TEMPLATE_ACCOUNT_ACTIVATION, parameters);
				
				// sends messages
				EmailSender sender = new EmailSender();
				sender.sendMessage(clientDTO.getEmail(), emailForClient);
			} catch (Error ex) {
				message = "Internal error. Cannot send email.";
				log.error("errorMessage --> " + message);
			}
		}

		// convert message to json
		json = gson.toJson(jsonMessage);
		log.trace("json string ->" + json);

		// write json object in PrintWriter
		try {
			res.getWriter().write(json);
		} catch (IOException ex) {
			message = "Can't write json in PrintWriter";
			log.error(message, ex);
			return forward;
		}
		
		log.debug("Command finished");
		return forward;
	}
}
