package ua.kharkiv.dereza.bookmaker.dao;

import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;

/**
 * Basic interface for all TrialDAO
 * 
 * @author Eduard
 *
 */
public interface TrialDAO {
	
	/**
	 * Returns a trial with the given id
	 * 
	 * @param trial id
	 * @return TrialDTO
	 */
	public TrialDTO findTrialById(int id);
	
	/**
	 * Returns List<TrialDTO> with givenstatusId
	 * 
	 * @param statusId
	 * @return List<TrialDTO>
	 */
	public List<TrialDTO> findTrialsByStatusId(int statusId);
	
	/**
	 * Updates information about trial
	 * 
	 * @param trialDTO
	 */
	public void updateTrial(TrialDTO trialDTO);
	
	/**
	 * Create new trial
	 * 
	 * @param trialDTO
	 */
	public void createTrial(TrialDTO trialDTO);
	
	/**
	 * Delete trial with given id
	 * 
	 * @param id
	 */
	public void deleteTrialById(int id);
}
