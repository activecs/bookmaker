package ua.kharkiv.dereza.bookmaker.dao;

import ua.kharkiv.dereza.bookmaker.dto.DependenceAgeDistanceDTO;

/**
 * Basic interface for all DependenceAgeDistanceDAO
 * 
 * @author Eduard
 * 
 */
public interface DependenceAgeDistanceDAO {

	/**
	 * Returns a dependence with the given age and distance
	 * 
	 * @param age
	 * @param distanceId
	 * @return DependenceAgeDistanceDTO
	 */
	public DependenceAgeDistanceDTO findDependenceByAgeDistanceId(int age, int distanceId);
}
