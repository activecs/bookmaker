package ua.kharkiv.dereza.bookmaker.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.BetDTO;

/**
 * Basic interface for all BetDAO
 * 
 * @author Eduard
 *
 */
public interface BetDAO {
	
	/**
	 * for bets history each clients
	 * 
	 * @param clientId
	 * @return List<BetDTO>
	 */
	public List<BetDTO> findBetsByClientId(int clientId);
	
	/**
	 * in one trial for one horse
	 * 
	 * @return List<BetDTO>
	 */
	public List<BetDTO> findBetsByTrialIdHorseId(int trialId, int horseId);
	
	/**
	 * bets for checkout after end of trial
	 * 
	 * @return List<BetDTO>
	 */
	public List<BetDTO> findBetsByTrialId(int trialId);
	
	/**
	 * find bet by id
	 * 
	 * @return BetDTO
	 */
	public BetDTO findBetById(int betId);
	
	/**
	 * Extract BetDTO from resultSet
	 * 
	 * @param rs
	 * @return BetDTO
	 */
	public BetDTO extractBetDTO(ResultSet rs) throws SQLException;
	
	/**
	 * Create new bet
	 * 
	 * @param betDTO
	 */
	public void createBet(BetDTO betDTO);
}
