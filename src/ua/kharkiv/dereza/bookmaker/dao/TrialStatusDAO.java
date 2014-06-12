package ua.kharkiv.dereza.bookmaker.dao;

import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.TrialStatusDTO;

/**
 * Basic interface for all TrialStatusDAO
 * 
 * @author Eduard
 *
 */
public interface TrialStatusDAO {
	
	/**
	 * Returns a TrialStatusDTO with the given id
	 * 
	 * @param id
	 * @return TrialStatusDTO
	 */
	public TrialStatusDTO findTrialStatusById(int id);
	
	/**
	 * Returns a TrialStatusDTO with the given name
	 * 
	 * @param name
	 * @return TrialStatusDTO
	 */
	public TrialStatusDTO findTrialStatusByName(String name);
	
	/**
	 * Returns all statuses
	 * 
	 * @return List<TrialStatusDTO>
	 */
	public List<TrialStatusDTO> findAllTrialStatuses();
}
