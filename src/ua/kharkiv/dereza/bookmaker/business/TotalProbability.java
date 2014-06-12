package ua.kharkiv.dereza.bookmaker.business;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.core.Constants;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;

/**
 * Total probability
 * @author Eduard
 *
 */
public class TotalProbability {

	private static final Logger log = Logger.getLogger(TotalProbability.class);

	private int trialId;
	private StaticProbability staticPr = null;
	private StatisticProbability statisticPr = null;
	
	private Map<Integer, BigDecimal> staticProbability = null;
	private Map<Integer, BigDecimal> statisticProbability = null;
	private Map<Integer, BigDecimal> totalProbability = new HashMap<Integer, BigDecimal>();

	public TotalProbability(int trialId) {
		super();
		this.trialId = trialId;
		figureOutProbability();
	}

	private void figureOutProbability() {
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);

		staticPr = new StaticProbability(trialId);
		statisticPr = new StatisticProbability(trialId);
		staticProbability = staticPr.getProbability();
		statisticProbability = statisticPr.getProbability();

		for (Entry<Integer, BigDecimal> entry : staticProbability.entrySet()) {
			totalProbability.put(
					entry.getKey(),
					entry.getValue().add(statisticProbability.get(entry.getKey())).setScale(2));
		}

		BigDecimal summaryProbability = BigDecimal.valueOf(0);
		for (Entry<Integer, BigDecimal> entry : totalProbability.entrySet()) {
			summaryProbability = summaryProbability.add(entry.getValue());
		}
		
		// figures out probability
		for (Entry<Integer, BigDecimal> entry : totalProbability.entrySet()) {
			entry.setValue(entry.getValue().divide(summaryProbability, 2,
					BigDecimal.ROUND_HALF_UP));
		}
		log.info("Total probability -> " + totalProbability);
		
		// figures out and sets margin
		for (Entry<Integer, BigDecimal> entry : totalProbability.entrySet()) {
			BigDecimal probability = entry.getValue();
			BigDecimal winCoefficient = (BigDecimal.valueOf(1.).divide(
					probability, 2, BigDecimal.ROUND_HALF_UP))
					.multiply(BigDecimal.valueOf(1.).subtract(Constants.MARGIN));
			entry.setValue(winCoefficient.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		log.info("Winning coefficient -> " + totalProbability);
		
		// updates info in db
		TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
		TrialHorseDTO trialHorseDTO = null;
		for(Entry<Integer, BigDecimal> entry : totalProbability.entrySet()){
			trialHorseDTO = trialHorseDAO.findTrialHorseByTrialIdHorseId(trialId, entry.getKey());
			trialHorseDTO.setWinCoefficient(entry.getValue());
			trialHorseDAO.updateTrialHorseInfo(trialHorseDTO);
		}	
	}
}
