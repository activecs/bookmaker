package ua.kharkiv.dereza.bookmaker.command;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.business.TotalProbability;
import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.HorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.HorseStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.HorseDTO;
import ua.kharkiv.dereza.bookmaker.dto.HorseStatusDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;

/**
 * 
 * 
 * @author Eduard
 * 
 */
public class SaveTrialHorseCommand extends Command {

	private static final Logger log = Logger
			.getLogger(SaveTrialHorseCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");

		String forward = null;
		String message = null;

		String action = req.getParameter("action");
		String trialId = req.getParameter("trialId");
		String horseId = req.getParameter("horseId");
		String statusId = req.getParameter("statusId");
		String winCoefficient = req.getParameter("winCoefficient");
		log.debug("Got parameters action->" + action + ", trialId->" + trialId
				+ ", horseId->" + horseId + ", statusId->" + statusId
				+ ", winCoefficient->" + winCoefficient);

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);

		TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
		List<TrialHorseDTO> trialHorseList = trialHorseDAO
				.findTrialHorsesByTrialId(Integer.parseInt(trialId));

		// if you add horse to trial
		if (action.equals("addTrialHorse")) {
			// checks ability to add one more horse
			if (trialHorseList.size() >= 8) {
				forward = Constants.PAGE_ERROR_PAGE;
				message = "You can't add more than 8 horses ti one trial";
				log.error(message);
				req.setAttribute("errorMessage", message);
				req.setAttribute("title", "error");
				return forward;
			}

			// checks whether this horse is already added to trial
			for (TrialHorseDTO trialHorseDTO : trialHorseList) {
				if (trialHorseDTO.getHorseId() == Integer.parseInt(horseId)) {
					forward = Constants.PAGE_ERROR_PAGE;
					message = "You can't add one horse twice";
					log.error(message);
					req.setAttribute("errorMessage", message);
					return forward;
				}
			}

			// trialHorseDAO.addHorseToTrial();
			TrialHorseDTO trialHorseDTO = new TrialHorseDTO();

			// check existing horse
			HorseDAO horseDAO = mysqlFactory.getHorseDAO();
			HorseDTO horseDTO = horseDAO.findHorseById(Integer
					.parseInt(horseId));
			log.debug("Found horse in db ->" + horseDTO);
			if (horseDTO == null) {
				forward = Constants.PAGE_ERROR_PAGE;
				message = "Can't find horse with given id.It might is internal error";
				log.error(message);
				req.setAttribute("errorMessage", message);
				req.setAttribute("title", "error");
				return forward;
			}
			// sets horse id
			trialHorseDTO.setHorseId(horseDTO.getId());

			// sets horse status id
			HorseStatusDAO horseStatusDAO = mysqlFactory.getHorseStatusDAO();
			HorseStatusDTO horseStatusDTO = horseStatusDAO
					.findHorseStatusByName(Constants.HORSE_STATUSES_ACTIVE);
			trialHorseDTO.setHorseStatusId(horseStatusDTO.getId());

			// sets trial id
			TrialDAO trialDAO = mysqlFactory.getTrialDAO();
			TrialDTO trialDTO = trialDAO.findTrialById(Integer
					.parseInt(trialId));
			log.debug("Found trial in db->" + trialDTO);
			trialHorseDTO.setTrialId(trialDTO.getId());

			// add horse to trial
			trialHorseDAO.addHorseToTrial(trialHorseDTO);
			log.info("Horse with id->" + trialHorseDTO.getHorseId()
					+ ", was added to trial with id->"
					+ trialHorseDTO.getTrialId());

			// sets winning coefficient
			try {
				new TotalProbability(Integer.parseInt(trialId));
			} catch (Exception ex) {
				log.error("Error in totalProbability ->" + ex);
			}

		} else {
			// if you edit horse info in trial
			if (action.equals("editTrialHorse")) {
				HorseStatusDAO horseStatusDAO = mysqlFactory
						.getHorseStatusDAO();
				HorseStatusDTO horseStatusDTO = horseStatusDAO
						.findHorseStatusById(Integer.parseInt(statusId));

				// checks existing status
				if (horseStatusDTO == null) {
					forward = Constants.PAGE_ERROR_PAGE;
					message = "Can't find horse status with given id.It might is internal error";
					log.error(message);
					req.setAttribute("errorMessage", message);
					req.setAttribute("title", "error");
					return forward;
				}
				// gets TrialHorse from db before changes
				TrialHorseDTO trialHorseDTO = trialHorseDAO
						.findTrialHorseByTrialIdHorseId(
								Integer.parseInt(trialId),
								Integer.parseInt(horseId));
				if (trialHorseDTO == null) {
					forward = Constants.PAGE_ERROR_PAGE;
					message = "Can't find TrialHorse with given ids.It might is internal error";
					log.error(message);
					req.setAttribute("errorMessage", message);
					req.setAttribute("title", "error");
					return forward;
				}
				log.info("TrialHorse info before changes->" + trialHorseDTO);

				// updates information
				trialHorseDTO.setWinCoefficient(new BigDecimal(winCoefficient));
				trialHorseDTO.setHorseStatusId(Integer.parseInt(statusId));

				// writes changes to db
				trialHorseDAO.updateTrialHorseInfo(trialHorseDTO);
				log.info("TrialHorse was changed to->" + trialHorseDTO);
				
				// checks whether it is necessary to set winning coefficient
				if(horseStatusDTO.getName().equals(Constants.HORSE_STATUSES_DNF)){
					// sets winning coefficient
					try {
						new TotalProbability(Integer.parseInt(trialId));
					} catch (Exception ex) {
						log.error("Error in totalProbability ->" + ex);
					}
				}
			}
			
		}

		req.setAttribute("title", "trials");
		forward = Constants.PAGE_ADMIN_TRIALS;
		log.debug("Command finished");
		return forward;
	}
}