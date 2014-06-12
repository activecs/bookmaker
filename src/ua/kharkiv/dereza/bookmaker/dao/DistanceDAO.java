package ua.kharkiv.dereza.bookmaker.dao;

import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.DistanceDTO;


/**
 * Basic interface for all DistanceDAO
 * 
 * @author Eduard
 *
 */
public interface DistanceDAO {
	
	/**
	 * Returns a distance with the given id
	 * 
	 * @param distance id
	 * @return DistanceDTO
	 */
	public DistanceDTO findDistanceById(int id);
	
	/**
	 * Returns a id with the given status name
	 * 
	 * @param distance
	 * @return DistanceDTO
	 */
	public DistanceDTO findDistanceByDistance(int distance);
	
	/**
	 * Return all distances
	 * 
	 * @return List<DistanceDTO>
	 */
	public List<DistanceDTO> findAllDistances();
}
