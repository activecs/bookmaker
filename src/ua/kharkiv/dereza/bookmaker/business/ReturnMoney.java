package ua.kharkiv.dereza.bookmaker.business;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.BetDAO;
import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;
import ua.kharkiv.dereza.bookmaker.dto.BetDTO;
import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;
import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;

/**
 * Class is used for returning money to clients if trial was cancelled.
 * @author Eduard
 *
 */
public class ReturnMoney {
	
	private static final Logger log = Logger.getLogger(ReturnMoney.class);
	private TrialDTO trial = null;
	
	public ReturnMoney(TrialDTO trial) {
		this.trial = trial;
		returnMoney();
	}
	
	private void returnMoney(){
		// creates the required DAO generator
		MysqlDAOFactory mysqlFactory = (MysqlDAOFactory) DAOFactory
						.getDAOFactory(DAOFactory.MYSQL);
		
		BetDAO betDAO = mysqlFactory.getBetDAO();
		
		// gets all bets with given trial's id
		List<BetDTO> betList = betDAO.findBetsByTrialId(trial.getId());
		for(BetDTO bet : betList){
			BigDecimal betValue = bet.getValue();
			int clientId = bet.getClientId();
			ClientDAO clientDAO = mysqlFactory.getClientDAO();
			ClientDTO client = clientDAO.findClientById(clientId);
			client.setBalance(client.getBalance().add(betValue));
			clientDAO.updateClient(client);
			log.info("Money were returned to client with id->" + clientId);
		}
	}
}
