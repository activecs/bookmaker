package ua.kharkiv.dereza.bookmaker.business;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.BetDAO;
import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.BetDTO;
import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;

/**
 * Transfer won sum of money on client's account
 * @author Eduard
 *
 */
public class TransferWonSum {

	private static final Logger log = Logger.getLogger(TransferWonSum.class);
	private TrialDTO trial = null;

	public TransferWonSum(TrialDTO trial) {
		this.trial = trial;
		transfer();
	}

	private void transfer() {
		log.trace("Transfering won sum started");
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
				.getDAOFactory(DAOFactory.MYSQL);
		
		int trialId = trial.getId();
		
		TrialHorseDAO trialHorseDAO = mysqlFactory.getTrialHorseDAO();
		List<TrialHorseDTO> trialHorseDTOList = trialHorseDAO.findTrialHorsesByTrialId(trialId);
		
		for(TrialHorseDTO th: trialHorseDTOList){
			if(th.getPlace() == 1){
				int horseId = th.getHorseId();
				BetDAO betDAO = mysqlFactory.getBetDAO();
				log.trace("Going to do query with trialId=" + trialId + ", horseId=" + horseId);
				List<BetDTO> betList = betDAO.findBetsByTrialIdHorseId(trialId, horseId);
				for(BetDTO bet : betList){
					int clientId = bet.getClientId();
					BigDecimal betValue = bet.getValue();
					BigDecimal winCoef = th.getWinCoefficient();
					ClientDAO clientDAO = mysqlFactory.getClientDAO();
					ClientDTO client = clientDAO.findClientById(clientId);
					client.setBalance(client.getBalance().add(betValue.multiply(winCoef)));
					clientDAO.updateClient(client);
					log.info("Client with id->"+ clientId +",won->"+ betValue.multiply(winCoef));
				}
			}
		}
		log.trace("Transfering won sum finished");
	}
}
