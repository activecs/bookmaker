package ua.kharkiv.dereza.bookmaker.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;

/**
 * Deletes client.
 * 
 * @author Eduard
 *
 */
public class DeleteClientCommand extends Command{
	
	private static final Logger log = Logger.getLogger(DeleteClientCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		String forward = null;
		String clientIds[] = req.getParameterValues("clientIds");
		
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		ClientDAO clientDAO = mysqlFactory.getClientDAO();
		for(String clientId : clientIds){
			clientDAO.deleteClient(Integer.parseInt(clientId));
		}
		
		log.info("Were deleted clients with ids->" + clientIds.toString());
		forward = "/controller?command=clientListForward";
		log.debug("Command finished");
		return forward;
	}
}
