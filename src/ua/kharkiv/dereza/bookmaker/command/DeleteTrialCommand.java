package ua.kharkiv.dereza.bookmaker.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;

/**
 * Deletes trial.
 * 
 * @author Eduard
 *
 */
public class DeleteTrialCommand extends Command {

	private static final Logger log = Logger
			.getLogger(DeleteTrialCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");

		String trialId = req.getParameter("trialId");

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		// delete trial
		TrialDAO trialDAO = mysqlFactory.getTrialDAO();
		trialDAO.deleteTrialById(Integer.parseInt(trialId));
		log.info("Was deleted trial with id ->" + trialId);
		
		req.setAttribute("title", "trials");
		String forward = Constants.PAGE_ADMIN_TRIALS;
		log.debug("Command fifnished");
		return forward;
	}

}
