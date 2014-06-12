package ua.kharkiv.dereza.bookmaker.command;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.bean.TrackBean;
import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.DistanceDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrackDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.DistanceDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrackDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialStatusDTO;
import ua.kharkiv.dereza.bookmaker.util.BeanTranformer;

/**
 * Forwards to page where admin can edit information about trial.
 * 
 * @author Eduard
 *
 */
public class EditTrialForwardCommand extends Command {

	private static final Logger log = Logger
			.getLogger(EditTrialForwardCommand.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		log.debug("Command started");
		String trialId = req.getParameter("trialId");
		log.debug("Trial for editing ->" + trialId);

		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);

		// gets all tracks and converts them into bean
		TrackDAO trackDAO = mysqlFactory.getTrackDAO();
		List<TrackDTO> trackDTOList = trackDAO.findAllTracks();
		List<TrackBean> trackBeanList = new LinkedList<TrackBean>();
		BeanTranformer transformer = null;
		for (TrackDTO trackDTO : trackDTOList) {
			transformer = new BeanTranformer(trackDTO);
			trackBeanList.add((TrackBean) transformer.getBean());
		}
		log.trace("Tracks -> " + trackBeanList);

		// gets all distances
		DistanceDAO distanceDAO = mysqlFactory.getDistanceDAO();
		List<DistanceDTO> distanceDTOList = distanceDAO.findAllDistances();
		log.trace("Distances -> " + distanceDTOList);

		// get all trial statuses
		TrialStatusDAO trialStatusDAO = mysqlFactory.getTrialStatusDAO();
		List<TrialStatusDTO> trialStatusDTOList = trialStatusDAO
				.findAllTrialStatuses();
		log.trace("TrialStatuses -> " + trialStatusDTOList);

		// puts all necessary objects in request attribute
		req.setAttribute("tracks", trackBeanList);
		req.setAttribute("distances", distanceDTOList);
		req.setAttribute("trialStatuses", trialStatusDTOList);
		
		String forward = Constants.PAGE_EDIT_TRIAL;
		req.setAttribute("trialId", Integer.parseInt(trialId));
		req.setAttribute("title", "Edit trial");
		
		log.debug("Command finished");
		return forward;
	}
}
