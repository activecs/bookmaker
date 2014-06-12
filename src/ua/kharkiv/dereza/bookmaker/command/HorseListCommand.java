package ua.kharkiv.dereza.bookmaker.command;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ua.kharkiv.dereza.bookmaker.bean.HorseBean;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.HorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.HorseDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;
import ua.kharkiv.dereza.bookmaker.util.BeanTranformer;

/**
 * json String with horses which take part in trial.
 * 
 * @author Eduard
 * 
 */
public class HorseListCommand extends Command {

	public static final Logger log = Logger.getLogger(HorseListCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {

		log.debug("Command started");

		res.setContentType("application/json");
		String forward = null;
		String message = null;

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		// gets trial id
		String id = req.getParameter("trialId");
		log.trace("Trial id -> " + id);
		int trialId = -1;
		if(id != null){
			trialId = Integer.parseInt(id);
		}
		
		TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();

		HorseDAO horseDAO = mysqlFactory.getHorseDAO();
		HorseDTO horseDTO = null;
		
		// gets TrialHorseDTO with horses that take part in trial with id trialId
		List<TrialHorseDTO> trialHorseList = trialHorseDAO.findTrialHorsesByTrialId(trialId);
		log.trace("Found in db list of TrialHorseDTO -> " + trialHorseList);
		
		List<HorseBean> horseBeanList = new LinkedList<HorseBean>();
		
		BeanTranformer transformer = null;
		
		// gets Horse DTO and transform it to HorseBean with using trial id
		for (TrialHorseDTO trialHorseDTO : trialHorseList) {
			horseDTO = horseDAO.findHorseById(trialHorseDTO.getHorseId());
			transformer = new BeanTranformer(horseDTO, trialHorseDTO.getTrialId());
			HorseBean horseBean = (HorseBean)transformer.getBean();
			horseBeanList.add(horseBean);
		}
		// sorts horses
		Collections.sort(horseBeanList);
		
		// create instance of json converter
		Gson gson = new Gson();
		String json = gson.toJson(horseBeanList);
		log.trace("json string ->" + json);

		// write json object in PrintWriter
		try {
			res.getWriter().write(json);
		} catch (IOException ex) {
			message = "Can't write json in PrintWriter";
			log.error(message, ex);
			return forward;
		}

		log.debug("Command finished");
		return forward;
	}
}