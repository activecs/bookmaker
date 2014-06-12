package ua.kharkiv.dereza.bookmaker.business;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialStatusDAO;
import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialStatusDTO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.util.Sorter;

/**
 * Ends trials according trial's 'start time'. Also calls TransferWonSum.
 * @author Eduard
 * 
 */
public class TrialObserver extends Thread {

	private static final Logger log = Logger.getLogger(TrialObserver.class);

	private int min = 0;
	private int max = 25;

	public TrialObserver() {
		this.setDaemon(true);
		this.start();
		log.info("Daemon started");
	}

	@Override
	public void run() {
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
						.getDAOFactory(DAOFactory.MYSQL);
		
		TrialStatusDAO trialStatuDAO = mysqlFactory.getTrialStatusDAO();

		// gets id of status with name = idle
		TrialStatusDTO trialStatusDTO = trialStatuDAO.findTrialStatusByName(Constants.TRIAL_STATUSES_IDLE);
		int idleStatusId = trialStatusDTO.getId();

		// gets id of status with name = finished
		trialStatusDTO = trialStatuDAO.findTrialStatusByName(Constants.TRIAL_STATUSES_FINISHED);
		int finishedStatusId = trialStatusDTO.getId();

		TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();

		TrialDAO trialDAO = mysqlFactory.getTrialDAO();
		List<TrialDTO> list = null;
		Date checkedDate = null;

		boolean flag = true;
		try {
			while (flag) {

				// gets list of all unfinished trials
				list = trialDAO.findTrialsByStatusId(idleStatusId);
				for (TrialDTO trialDTO : list) {
					checkedDate = trialDTO.getStartTime();
					boolean passed = checkedDate.before(new Date(System.currentTimeMillis()));
					if (passed) {
						
						// gets all horses that involved to trial
						List<TrialHorseDTO> horses = trialHorseDAO.findTrialHorsesByTrialId(trialDTO.getId());
						log.trace("Before ->" + horses);
						
						// +/-
						for (TrialHorseDTO horse : horses) {
							BigDecimal random = getRandom();
							horse.setWinCoefficient(horse.getWinCoefficient()
									.add(horse.getWinCoefficient().multiply(
											random)));
						}
						// sorts horses by winning coefficient
						Collections.sort(horses, Sorter.sortTrialHorseByWinCoefficient);
						log.trace("In process ->" + horses);
						// updates places
						int place = 1;
						for (TrialHorseDTO horse : horses) {
							horse.setPlace(place);
							place++;
						}
						// get TrialHorseDTO one more time 
						List<TrialHorseDTO> horsesDefaultWinCoef = trialHorseDAO.findTrialHorsesByTrialId(trialDTO.getId());
						
						// puts horsesDefaultWinCoef in Map<horseId, TrialHorseDTO>
						Map<Integer, TrialHorseDTO> tempMap = new HashMap<Integer, TrialHorseDTO>(); 
						for(TrialHorseDTO horseDefault : horsesDefaultWinCoef){
							tempMap.put(horseDefault.getHorseId(), horseDefault);
						}
						
						// replace default winCoefficient and put it in db
						for(TrialHorseDTO horse : horses){
							horse.setWinCoefficient(tempMap.get(horse.getHorseId()).getWinCoefficient());
							trialHorseDAO.updateTrialHorseInfo(horse);
						}
						
						log.trace("After ->" + horses);
						log.trace("triakDTO for update ->" + trialDTO);
						
						//TODO fix bug
						trialDTO.setTrialStatusId(finishedStatusId);
						trialDAO.updateTrial(trialDTO);
						log.info("Trial finished, with id -> " + trialDTO.getId());
						
						// transfer won sum of money to client's account
						try{
							new TransferWonSum(trialDTO);
						}catch(Exception ex){
							log.error("Cannot transfer won sum", ex);
						}
							
					}
				}
				sleep(50 * 1000);
			}
		} catch (InterruptedException ex) {
			log.error("Daemon interruptedEroor", ex);
		}
	}

	private BigDecimal getRandom() {
		Random random = new Random();
		int temp = random.nextInt(max);
		temp = temp < min ? temp + min : temp;
		if (Math.random() < 0.5) {
			temp *= -1;
		}
		BigDecimal res = new BigDecimal(temp).divide(new BigDecimal(100), 2,
				BigDecimal.ROUND_HALF_UP);
		return res;
	}
}