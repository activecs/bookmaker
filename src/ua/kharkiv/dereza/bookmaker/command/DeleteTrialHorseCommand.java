package ua.kharkiv.dereza.bookmaker.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ua.kharkiv.dereza.bookmaker.business.TotalProbability;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;

/**
 * Deletes horse from trial.
 * 
 * @author Eduard
 *
 */
public class DeleteTrialHorseCommand extends Command{
	
	private static final Logger log = Logger.getLogger(DeleteTrialHorseCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		
		res.setContentType("application/json");
		String forward = null;
		
		String trialId = req.getParameter("trialId");
		String horseId = req.getParameter("horseId");
		log.debug("trial id ->" + trialId + ", horse id -> " + horseId);
		
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		
		// check existing record
		TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
		TrialHorseDTO trialHorseDTO = null;
		try{
			trialHorseDTO = trialHorseDAO.findTrialHorseByTrialIdHorseId(Integer.parseInt(trialId), Integer.parseInt(horseId));
		}catch(NumberFormatException ex){
			log.error("Can't parse int ->" + ex);
			return forward;
		}
		if(trialHorseDTO == null){
			log.debug("Record doesn't exist");
			return forward;
		}
		log.debug("Found in db by trial and horse ids -> " + trialHorseDTO);
		
		trialHorseDAO.deleteTrialHorseByTrialIdHorseId(trialHorseDTO.getTrialId(), trialHorseDTO.getHorseId());
		log.info("Horse with id ->" + trialHorseDTO.getHorseId() +" was deleted from trial with id ->" + trialHorseDTO.getTrialId());
		
		try{
		// sets winning coefficient
		new TotalProbability(Integer.parseInt(trialId));
		}catch(Exception ex){
			log.error("Can't sets winning coefficient ->" + ex);
		}
		
		// create instance of json converter
		String message = "ok";
		Gson gson = new Gson();
		String json = gson.toJson(message);
		try {
			res.getWriter().write(json);
		} catch (IOException ex) {
			message = "Can't write json to response";
			log.error(message, ex);
		}
		
		log.debug("Command finished");
		return forward;
	}
	
}
