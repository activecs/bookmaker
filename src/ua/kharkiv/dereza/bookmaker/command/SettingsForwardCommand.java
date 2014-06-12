package ua.kharkiv.dereza.bookmaker.command;

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
 * Forwards to page with client's account settings
 * 
 * @author Eduard
 * 
 */
public class SettingsForwardCommand extends Command {

	private static final Logger log = Logger
			.getLogger(SettingsForwardCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		String forward = null;
		
		ClientBean clientBean = (ClientBean) req.getSession().getAttribute("clientbean");

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		// creates DAO for client
		ClientDAO clientDAO = mysqlFactory.getClientDAO();
		ClientDTO clientDTO = clientDAO.findClientByLogin(clientBean.getLogin());
		
		// transforms dto to bean
		BeanTranformer transformer = new BeanTranformer(clientDTO);
		clientBean = (ClientBean)transformer.getBean();
		// attach bean to session
		req.getSession().setAttribute("clientbean", clientBean);
		
		req.setAttribute("balance", clientBean.getBalance());
		req.setAttribute("name", clientBean.getName());
		req.setAttribute("surname", clientBean.getSurname());
		req.setAttribute("email", clientBean.getEmail());

		req.setAttribute("title", "Settings");
		forward = Constants.PAGE_SETTINGS;
		log.debug("Command finished");
		return forward;
	}

}
