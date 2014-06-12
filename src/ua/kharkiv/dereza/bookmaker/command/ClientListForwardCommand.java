package ua.kharkiv.dereza.bookmaker.command;

import java.util.LinkedList;
import java.util.List;

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
 * List of clients.
 * 
 * @author Eduard
 *
 */
public class ClientListForwardCommand extends Command {

	private static final Logger log = Logger.getLogger(ClientListForwardCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		String forward = null;
		
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		ClientDAO clientDAO = mysqlFactory.getClientDAO();
		List<ClientDTO> clientsDTO = clientDAO.findAllClients();
		List<ClientBean> clients = new LinkedList<ClientBean>();
		BeanTranformer bt = null;
		for(ClientDTO dto: clientsDTO){
			bt = new BeanTranformer(dto);
			clients.add((ClientBean)bt.getBean());
		}
		
		req.setAttribute("clients", clients);
		req.setAttribute("title", "Clients");
		log.trace("Clients ->" + clients);
		forward = Constants.PAGE_CLIENTS;
		log.debug("Command finished");
		return forward;
	}
}