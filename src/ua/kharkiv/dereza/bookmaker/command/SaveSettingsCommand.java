package ua.kharkiv.dereza.bookmaker.command;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.bean.ClientBean;
import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;
import ua.kharkiv.dereza.bookmaker.util.BeanTranformer;

/**
 * Saves settings.
 * 
 * @author Eduard
 *
 */
public class SaveSettingsCommand extends Command {

	private static final Logger log = Logger.getLogger(SaveSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		String forward = null;
		
		// gets parameters from request
		String name = req.getParameter("name");
		String surname = req.getParameter("surname");
		String email = req.getParameter("email");
		String strBalance = req.getParameter("balance").replace(",", ".");
		BigDecimal balance = new BigDecimal(strBalance);
		log.trace("Got parameters -> name=" + name + ", surname=" + surname + ", email=" + email + ", balance=" + balance.toString());
		
		ClientBean clientBean = (ClientBean)(req.getSession().getAttribute("clientbean"));
		
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		// finds client in db
		ClientDAO clientDAO = mysqlFactory.getClientDAO();
		ClientDTO clientDTO = clientDAO.findClientById(clientBean.getId());
		
		// update info about client
		clientDTO.setName(name);
		clientDTO.setSurname(surname);
		clientDTO.setEmail(email);
		clientDTO.setBalance(balance);
		
		// updates info in db
		clientDAO.updateClient(clientDTO);
		
		ClientDTO updatedClientDTO = clientDAO.findClientById(clientBean.getId()); 
		
		// updates clientbean in session
		BeanTranformer transformer = new BeanTranformer(updatedClientDTO);
		ClientBean updatedClientBean = (ClientBean)transformer.getBean();
		req.getSession().setAttribute("clientbean", updatedClientBean);
		
		String role = clientBean.getRole();
		if (role.equals(Constants.ROLE_ADMIN))
			forward = Constants.PAGE_ADMIN_TRIALS;
		if (role.equals(Constants.ROLE_CLIENT))
			forward = Constants.PAGE_CLIENT_TRIALS;
		
		req.setAttribute("title", "trials");
		log.debug("Command finished");
		return forward;
	}

}
