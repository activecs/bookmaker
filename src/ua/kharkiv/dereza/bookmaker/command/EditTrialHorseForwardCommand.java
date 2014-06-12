package ua.kharkiv.dereza.bookmaker.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.HorseStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.HorseStatusDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;

/**
 * Forwards to page where admin can edit information about horse that takes part
 * in trial.
 * 
 * @author Eduard
 * 
 */
public class EditTrialHorseForwardCommand extends Command{
	
	private static final Logger log = Logger.getLogger(EditTrialHorseForwardCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		
		String trialId = req.getParameter("trialId");
		String horseId = req.getParameter("horseId");
		req.setAttribute("trialId", trialId);
		req.setAttribute("horseId", horseId);
		log.debug("Trial with id->" + trialId + ", horse with id->" +horseId);
		
		String forward = null;

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		
		// checks existing TrialHorse with given trialId and horseId
		TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
		TrialHorseDTO trialHorseDTO = trialHorseDAO.findTrialHorseByTrialIdHorseId(Integer.parseInt(trialId), Integer.parseInt(horseId));
		log.debug("Found in db->" + trialHorseDTO);
		String message = null;
		if(trialHorseDTO == null){
			forward = Constants.PAGE_ERROR_PAGE;
			message = "Can't find TrialHorse with given trialId and horseId";
			log.error(message);
			req.setAttribute("errorMessage", message);
			req.setAttribute("title", "error");
			return forward;
		}
		
		// gets all horse statuses
		HorseStatusDAO horseStatusDAO = mysqlFactory.getHorseStatusDAO();
		List<HorseStatusDTO> horseStatusDTOList = horseStatusDAO.finfAllHorseStatus();
		if(horseStatusDTOList == null){
			forward = Constants.PAGE_ERROR_PAGE;
			message = "Can't find horse status. Internal Error";
			log.error(message);
			req.setAttribute("errorMessage", message);
			req.setAttribute("title", "error");
			return forward;
		}
		
		// adds statuses to request
		req.setAttribute("statuses", horseStatusDTOList);
		
		// adds win coefficient to request
		req.setAttribute("winCoefficient", trialHorseDTO.getWinCoefficient());
		
		req.setAttribute("title", "Edit horse");
		forward = Constants.PAGE_EDIT_TRIAL_HORSE;
		log.debug("Command finished");
		return forward;
	}
}
