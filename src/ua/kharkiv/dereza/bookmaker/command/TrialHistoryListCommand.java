package ua.kharkiv.dereza.bookmaker.command;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.bean.TrialBean;
import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;
import ua.kharkiv.dereza.bookmaker.util.BeanTranformer;

import com.google.gson.Gson;

/**
 * List of finished and canceled trials
 * 
 * @author Eduard
 *
 */
public class TrialHistoryListCommand extends Command{
	
	private static final Logger log = Logger.getLogger(TrialHistoryForwardCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");

		res.setContentType("application/json");
		String forward = null;
		String message = null;

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		// creates necessary DAO
		TrialDAO trialDAO = mysqlFactory.getTrialDAO();
		TrialStatusDAO trialStatusDAO = mysqlFactory.getTrialStatusDAO();
		
		// gets trials with status = finished
		int trialFinishedStatusId = trialStatusDAO.findTrialStatusByName(
				Constants.TRIAL_STATUSES_FINISHED).getId();
		List<TrialDTO> finishedList = trialDAO.findTrialsByStatusId(trialFinishedStatusId);
		
		// gets trials with status = finished
		int trialCanceledStatusId = trialStatusDAO.findTrialStatusByName(Constants.TRIAL_STATUSES_CANCELED).getId();
		List<TrialDTO> canceledList = trialDAO.findTrialsByStatusId(trialCanceledStatusId);
		
		// merge lists
		finishedList.addAll(canceledList);
		
		// transforms dto to bean
		List<TrialBean> beanList = new LinkedList<TrialBean>();
		BeanTranformer transformer;
		for(TrialDTO dto: finishedList){
			transformer = new BeanTranformer(dto);
			beanList.add((TrialBean)transformer.getBean());
		}
		// sorts trials
		Collections.sort(beanList);
		
		// create instance of json converter
		Gson gson = new Gson();
		String json = gson.toJson(beanList);
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
