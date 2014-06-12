package ua.kharkiv.dereza.bookmaker.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.bean.HorseBean;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.DependenceAgeCoverDAO;
import ua.kharkiv.dereza.bookmaker.dao.DependenceAgeDistanceDAO;
import ua.kharkiv.dereza.bookmaker.dao.DependenceAgeTrackTypeDAO;
import ua.kharkiv.dereza.bookmaker.dao.HorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrackDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.DependenceAgeCoverDTO;
import ua.kharkiv.dereza.bookmaker.dto.DependenceAgeDistanceDTO;
import ua.kharkiv.dereza.bookmaker.dto.DependenceAgeTrackTypeDTO;
import ua.kharkiv.dereza.bookmaker.dto.HorseDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrackDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;
import ua.kharkiv.dereza.bookmaker.util.BeanTranformer;

/**
 * Static component of total probability
 * @author Eduard
 *
 */
public class StaticProbability {

	private static final Logger log = Logger.getLogger(StaticProbability.class);

	private int trialId;
	private Map<Integer, BigDecimal> tempProbability = null;
	private BigDecimal summaryCoefficient = BigDecimal.valueOf(0);
	private Map<Integer, BigDecimal> probability = null; // horseId, probability 

	public StaticProbability(int trialId) {
		super();
		this.trialId = trialId;
		figureOutProbability();
	}

	private void figureOutProbability() {
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		
		// created necessary dao
		TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
		TrialDAO trialDAO = mysqlFactory.getTrialDAO();
		HorseDAO horseDAO = mysqlFactory.getHorseDAO();
		TrackDAO trackDAO = mysqlFactory.getTrackDAO();
		DependenceAgeCoverDAO dependenceAgeCoverDAO = mysqlFactory.getDependenceAgeCoverDAO();
		DependenceAgeDistanceDAO dependenceAgeDistanceDAO = mysqlFactory.getDependenceAgeDistanceDAO();
		DependenceAgeTrackTypeDAO dependenceAgeTrackTypeDAO = mysqlFactory.getDependenceAgeTrackTypeDAO();
		
		// gets TrialDTO and TrackDTO
		TrialDTO trialDTO = trialDAO.findTrialById(trialId);
		TrackDTO trackDTO = trackDAO.findTrackById(trialDTO.getTrackId());
		
		
		BeanTranformer bt = null;
		List<TrialHorseDTO> trialHorseDTOList = trialHorseDAO.findTrialHorsesByTrialId(trialId);
		tempProbability = new HashMap<Integer, BigDecimal>();
		for(TrialHorseDTO dto : trialHorseDTOList){
			Integer ageCover = 0;
			Integer ageDistance = 0;
			Integer ageTrackType = 0;
			BigDecimal coefficient = BigDecimal.valueOf(0);
			
			// ageCover
			HorseDTO horseDTO = horseDAO.findHorseById(dto.getHorseId());
			bt = new BeanTranformer(horseDTO);
			HorseBean horseBean = (HorseBean)bt.getBean();
			DependenceAgeCoverDTO dependenceAgeCoverDTO = dependenceAgeCoverDAO
					.findDependenceByAgeCover(horseBean.getAge(), trackDTO.getCoverId());
			ageCover = dependenceAgeCoverDTO.getChance();
			
			// ageDistance
			DependenceAgeDistanceDTO dependenceAgeDistanceDTO = dependenceAgeDistanceDAO
					.findDependenceByAgeDistanceId(horseBean.getAge(), trialDTO.getDistanceId());
			ageDistance = dependenceAgeDistanceDTO.getChance();
			
			// ageTrackType
			DependenceAgeTrackTypeDTO dependenceAgeTrackTypeDTO = dependenceAgeTrackTypeDAO
					.findDependenceByAgeTrackTypeId(horseBean.getAge(), trackDTO.getTrackTypeId());
			ageTrackType = dependenceAgeTrackTypeDTO.getChance();
			
			coefficient = BigDecimal.valueOf(ageCover * ageDistance * ageTrackType);
			summaryCoefficient = summaryCoefficient.add(coefficient);
			tempProbability.put(horseDTO.getId(), coefficient);
			log.debug("SummaryCoefficient ->" + summaryCoefficient);
			log.debug("TempProbability ->" + tempProbability);
		}
		
		probability = new HashMap<Integer, BigDecimal>();
		for (Map.Entry<Integer,BigDecimal> entry : tempProbability.entrySet()) {
			BigDecimal probabilityValue = entry.getValue().divide(summaryCoefficient, 2, BigDecimal.ROUND_HALF_UP);
			probability.put(entry.getKey(), probabilityValue);
		}
		log.debug("Static probability for trial with id->" + trialId + "\n" + probability);
	}

	public Map<Integer, BigDecimal> getProbability() {
		return probability;
	}
}
