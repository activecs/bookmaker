package ua.kharkiv.dereza.bookmaker.command;

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
 * Remove ban from client
 * 
 * @author Eduard
 *
 */
public class UnbanClientCommand extends Command{
	
	private static final Logger log = Logger.getLogger(UnbanClientCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		String forward = null;
		String clientIds[] = req.getParameterValues("clientIds");
		
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		ClientDAO clientDAO = mysqlFactory.getClientDAO();
		ClientStatusDAO clientStatusDAO = mysqlFactory.getClientStatusDAO();
		for(String clientId : clientIds){
			ClientDTO clientDTO = clientDAO.findClientById(Integer.parseInt(clientId));
			ClientStatusDTO statusDTO = clientStatusDAO.findClientStatusByName(Constants.CLIENT_STATUSES_ACTIVE);
			clientDTO.setClientStatusId(statusDTO.getId());
			clientDAO.updateClient(clientDTO);
		}
		
		log.info("Were unbanned clients with ids->" +clientIds.toString());
		forward = "/controller?command=clientListForward";
		log.debug("Command finished");
		return forward;
	}
}
