package ua.kharkiv.dereza.bookmaker.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;

/**
 * Basic interface for all TrialHorseDAO
 * 
 * @author Eduard
 *
 */
public interface TrialHorseDAO {
	
	/**
	 * Returns a TrialHorseDTO with the given trial and horse id
	 * 
	 * @param trial id
	 * @param horse id
	 * @return TrialHorseDTO
	 */
	public TrialHorseDTO findTrialHorseByTrialIdHorseId(int trialId, int horseId);
	
	/**
	 * Returns a TrialHorseDTO with the given id
	 * 
	 * @param id
	 * @return TrialHorseDTO
	 */
	public TrialHorseDTO findTrialHorseById(int id);
	
	/**
	 * Deletes horse from trial
	 * 
	 * @param trialId
	 * @param horseId
	 */
	public void deleteTrialHorseByTrialIdHorseId(int trialId, int horseId);
	
	/**
	 * Adds horse to trial
	 * 
	 * @param trialHorseDTO
	 */
	public void addHorseToTrial(TrialHorseDTO trialHorseDTO);
	
	/**
	 * Returns a list of TrialHorseDTO with the given trial
	 * 
	 * @param trial id
	 * @return List<TrialHorseDTO>
	 */
	public List<TrialHorseDTO> findTrialHorsesByTrialId(int trialId); 
	
	/**
	 * Returns a list of TrialHorseDTO with the given horse
	 * 
	 * @param horseId
	 * @return List<TrialHorseDTO>
	 */
	public List<TrialHorseDTO> findTrialHorsesByHorseId(int horseId);
	
	/**
	 * Update info about horse in trial
	 * 
	 * @param trialHorseDTO
	 */
	public void updateTrialHorseInfo(TrialHorseDTO trialHorseDTO);
	
	/**
	 * Extract TrialHorseDTO from resultSet
	 * 
	 * @param rs
	 * @return TrialHorseDTO
	 */
	public TrialHorseDTO extractTrialHorseDTO(ResultSet rs) throws SQLException;
}
