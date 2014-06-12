package ua.kharkiv.dereza.bookmaker.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.business.ReturnMoney;
import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialStatusDTO;

/**
 * Saves changes in trial's information or creates new trial.
 * 
 * @author Eduard
 *
 */
public class SaveTrialCommand extends Command {

	private static final Logger log = Logger.getLogger(SaveTrialCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");

		String forward = null;

		// gets parameters
		String trackId = req.getParameter("trackId");
		String distanceId = req.getParameter("distanceId");
		String trialStatusId = req.getParameter("trialStatusId");
		String trialId = req.getParameter("trialId");
		
		String startTime = req.getParameter("startTime");
		log.trace("Got time from page -> " + startTime);

		String action = req.getParameter("action");
		log.debug("Action with trial -> " + action);
		
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);

		TrialDAO trialDAO = mysqlFactory.getTrialDAO();
		TrialDTO trialDTO = new TrialDTO();
		trialDTO.setTrackId(Integer.parseInt(trackId));
		trialDTO.setDistanceId(Integer.parseInt(distanceId));
		trialDTO.setTrialStatusId(Integer.parseInt(trialStatusId));
		
		TrialStatusDAO trialStatusDAO = mysqlFactory.getTrialStatusDAO();
		TrialStatusDTO statusDTO = trialStatusDAO.findTrialStatusById(Integer.parseInt(trialStatusId));
		if(statusDTO.getName().equals(Constants.TRIAL_STATUSES_CANCELED)){
			new ReturnMoney(trialDTO);
		}
		
		// parse start time
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = dateFormat.parse(startTime);
			if(date.getYear() < 0){
				dateFormat = new SimpleDateFormat(
						"dd-MM-yyyy HH:mm:ss");
				date = dateFormat.parse(startTime);
			}
			log.trace("Parced date ->" + date.toString());
		} catch (ParseException ex) {
			log.error("Can't parse date", ex);
		}
		trialDTO.setStartTime(date);

		if (action.equals("addTrial")) {
			trialDAO.createTrial(trialDTO);
			log.info("Was created new trial ->" + trialDTO);
		} else if (action.equals("editTrial")) {
			trialDTO.setId(Integer.parseInt(trialId));
			trialDAO.updateTrial(trialDTO);
			log.info("Was edited new trial ->" + trialDTO);
		}
		// sets title for page
		req.setAttribute("title", "trials");

		forward = Constants.PAGE_ADMIN_TRIALS;
		log.debug("Command finished");
		return forward;
	}
}