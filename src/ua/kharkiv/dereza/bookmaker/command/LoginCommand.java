package ua.kharkiv.dereza.bookmaker.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.bean.ClientBean;
import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.core.PasswordEncryptor;
import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dao.ClientStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;
import ua.kharkiv.dereza.bookmaker.dto.ClientStatusDTO;
import ua.kharkiv.dereza.bookmaker.util.BeanTranformer;

/**
 * Loggin command
 * 
 * @author Eduard
 *
 */
public class LoginCommand extends Command {

	private static final Logger log = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {

		log.debug("Command started");
		
		// creates new session
		HttpSession session = req.getSession(true);

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);

		// creates DAO for client
		ClientDAO clientDAO = mysqlFactory.getClientDAO();
		String login = req.getParameter("login");
		String password = req.getParameter("password");

		// gets md5-hash code of password
		PasswordEncryptor encryptor = new PasswordEncryptor();
		password = encryptor.encode(password);

		// searches client in db with given login
		log.trace("Request parameter: login --> " + login);
		ClientDTO clientDTO = clientDAO.findClientByLogin(login);
		log.trace("Found client in db --> " + clientDTO);
		
		String status = null;
		if(clientDTO != null){
			// check client status
			ClientStatusDAO clientStatusDAO = mysqlFactory.getClientStatusDAO();
			ClientStatusDTO clientStatusDTO = clientStatusDAO.findClientStatusById(clientDTO.getClientStatusId());
			status = clientStatusDTO.getName();			
		}

		// error handler
		String message = null;
		String forward = Constants.PAGE_ERROR_PAGE;
		if (clientDTO == null || password == null || !clientDTO.getPassword().equals(password) ) {
			message = "Cannot find user with such login/password";
			req.setAttribute("errorMessage", message);
			req.setAttribute("title", "error");
			log.error("errorMessage --> " + message);
			return forward;
		}
		
		if(!Constants.CLIENT_STATUSES_ACTIVE.equals(status)){
			message = "Your account isn't active";
			req.setAttribute("errorMessage", message);
			req.setAttribute("title", "error");
			log.error("errorMessage --> " + message);
			return forward;
		}
		
		// transforms dto to bean
		BeanTranformer transformer = new BeanTranformer(clientDTO);
		ClientBean clientBean = (ClientBean)transformer.getBean();
		
		// attach bean to session
		session.setAttribute("clientbean", clientBean);
		req.setAttribute("title", "trials");
		
		if (clientBean.getRole().equals(Constants.ROLE_ADMIN))
			forward = Constants.PAGE_ADMIN_TRIALS;
		if (clientBean.getRole().equals(Constants.ROLE_CLIENT))
			forward = Constants.PAGE_CLIENT_TRIALS;
		
		log.info("Logged in new client --> " + login);
		log.debug("ClientBean --> " + clientBean);
		log.debug("Command finished");
		return forward;
	}

}