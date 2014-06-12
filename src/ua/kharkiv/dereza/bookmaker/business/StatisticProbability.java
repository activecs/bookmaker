package ua.kharkiv.dereza.bookmaker.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialStatusDTO;

/**
 * Statistic component of total probability
 * @author Eduard
 *
 */
public class StatisticProbability {
	
	private static final Logger log = Logger.getLogger(StatisticProbability.class);
	
	private int trialId;
	private Map<Integer, BigDecimal> aproximatePlaces = null;
	private Map<Integer, BigDecimal> probability = null;
	
	public StatisticProbability(int trialId) {
		super();
		this.trialId = trialId;
		figureOutProbability();
	}
	
	private void figureOutProbability(){
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		
		// created necessary dao
		TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
		TrialStatusDAO trialStatusDAO = mysqlFactory.getTrialStatusDAO();
		TrialDAO trialDAO = mysqlFactory.getTrialDAO();
		
		// finds id of status with name = finished
		TrialStatusDTO trialStatus = trialStatusDAO.findTrialStatusByName(Constants.TRIAL_STATUSES_FINISHED);
		int finishedTrial = trialStatus.getId();
		
		// gets all horses that take part in this trial
		List<TrialHorseDTO> horses = trialHorseDAO.findTrialHorsesByTrialId(trialId);
		probability = new HashMap<Integer, BigDecimal>();
		aproximatePlaces = new HashMap<Integer, BigDecimal>();
		
		// for each horse that take part in this trial
		// 
		for(TrialHorseDTO horse : horses){
			int horseId = horse.getHorseId();
			
			// finds the races in which the horse was involved
			List<TrialHorseDTO> trials = trialHorseDAO.findTrialHorsesByHorseId(horseId);
			log.trace("Found in db all races in which the horse with id->" + horseId + " was involved" + trials);
			double aproximatePlace = 0;
			double placeSum = 0; 
			int trialCount = 0;
			
			// summarizes the horse wins
			for(TrialHorseDTO trial : trials){
				
				// checks whether the race is finished
				int tempTrialId = trial.getTrialId();
				int trialStatusId = trialDAO.findTrialById(tempTrialId).getTrialStatusId();
				if(trialStatusId != finishedTrial) continue;
				
				placeSum = trial.getPlace();
				trialCount ++;
			}
			
			if(trialCount != 0){
				aproximatePlace = placeSum/trialCount;
			}else{
				aproximatePlace = 0;
			}
			aproximatePlaces.put(horseId, BigDecimal.valueOf(aproximatePlace).setScale(2));
		}
		
		BigDecimal placeSum = new BigDecimal(0);
		for(Map.Entry<Integer, BigDecimal> entry : aproximatePlaces.entrySet()){
			placeSum = placeSum.add(entry.getValue());
		}
		
		for(Map.Entry<Integer, BigDecimal> entry : aproximatePlaces.entrySet()){
			if(entry.getValue().intValue() != 0){
				entry.setValue(placeSum.divide(entry.getValue(), 2, BigDecimal.ROUND_HALF_UP));
			}else{
				entry.setValue(BigDecimal.valueOf(0));
			}
		}
		
		placeSum = BigDecimal.valueOf(0);
		for(Map.Entry<Integer, BigDecimal> entry : aproximatePlaces.entrySet()){
			placeSum = placeSum.add(entry.getValue());
		}
		
		for(Map.Entry<Integer, BigDecimal> entry : aproximatePlaces.entrySet()){
			if(entry.getValue().intValue() != 0 && placeSum.intValue() != 0){
				entry.setValue(entry.getValue().divide(placeSum, 2, BigDecimal.ROUND_HALF_UP));
			}else{
				entry.setValue(BigDecimal.valueOf(0));
			}
		}
		probability = aproximatePlaces;
		
		log.debug("Statistic probability for trial with id->" + trialId + "\n" + probability);
	}
	
	public Map<Integer, BigDecimal> getProbability(){
		return probability;
	}
}
