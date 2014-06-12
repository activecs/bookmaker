package ua.kharkiv.dereza.bookmaker.dao;

import ua.kharkiv.dereza.bookmaker.dto.DependenceAgeTrackTypeDTO;

/**
 * Basic interface for all DependenceAgeTrackTypeDAO
 * 
 * @author Eduard
 *
 */
public interface DependenceAgeTrackTypeDAO {
	
	/**
	 * Returns a dependence with the given age and track type
	 * 
	 * @param age
	 * @param trackTypeId
	 * @return DependenceAgeTrackTypeDAO
	 */
	public DependenceAgeTrackTypeDTO findDependenceByAgeTrackTypeId(int age, int trackTypeId);
}
