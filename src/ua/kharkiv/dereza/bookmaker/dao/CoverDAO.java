package ua.kharkiv.dereza.bookmaker.dao;

import ua.kharkiv.dereza.bookmaker.dto.CoverDTO;

/**
 * Basic interface for all CoverDAO
 * 
 * @author Eduard
 *
 */
public interface CoverDAO {
	
	/**
	 * Returns a cover with the given id
	 * 
	 * @param cover id
	 * @return CoverDTO
	 */
	public CoverDTO findCoverById(int id);
	
	/**
	 * Returns a id with the given status name
	 * 
	 * @param cover name
	 * @return CoverDTO
	 */
	public CoverDTO findCoverByName(String name);
}
