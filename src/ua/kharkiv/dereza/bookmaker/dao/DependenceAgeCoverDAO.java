package ua.kharkiv.dereza.bookmaker.dao;

import ua.kharkiv.dereza.bookmaker.dto.DependenceAgeCoverDTO;

/**
 * Basic interface for all DependenceAgeCoverDAO
 * 
 * @author Eduard
 *
 */
public interface DependenceAgeCoverDAO {
	
	/**
	 * Returns a dependence with the given age and cover id
	 * 
	 * @param age
	 * @param coverId
	 * @return
	 */
	public DependenceAgeCoverDTO findDependenceByAgeCover(int age, int coverId);
}
