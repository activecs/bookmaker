package ua.kharkiv.dereza.bookmaker.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.bean.HorseBean;
import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.HorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.HorseDTO;
import ua.kharkiv.dereza.bookmaker.util.BeanTranformer;

/**
 * Forwards admin to page where he can add horse to trial.
 * 
 * @author Eduard
 *
 */
public class AddTrialHorseForwardCommand extends Command{
	
	private static final Logger log = Logger.getLogger(AddTrialHorseForwardCommand.class);
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		
		String trialId = req.getParameter("trialId");
		req.setAttribute("trialId", trialId);
		
		String forward = null;

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		
		HorseDAO horseDAO = mysqlFactory.getHorseDAO();
		
		List<HorseDTO> horseDTOList = horseDAO.findAllHorses();
		List<HorseBean> horseBeanList = new LinkedList<HorseBean>();
		BeanTranformer beanTranformer = null;
		for(HorseDTO horseDTO : horseDTOList){
			beanTranformer = new BeanTranformer(horseDTO);
			horseBeanList.add((HorseBean)beanTranformer.getBean());
		}
		// sorts horses
		Collections.sort(horseBeanList);
		
		req.setAttribute("title", "Add horse to trial");
		req.setAttribute("horses", horseBeanList);
		forward = Constants.PAGE_ADD_TRIAL_HORSE;
		
		log.debug("Command finished");
		return forward;
	}
}
