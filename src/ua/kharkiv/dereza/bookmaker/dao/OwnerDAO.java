package ua.kharkiv.dereza.bookmaker.dao;

import ua.kharkiv.dereza.bookmaker.dto.OwnerDTO;

/**
 * Basic interface for all OwnerDAO
 * 
 * @author Eduard
 *
 */
public interface OwnerDAO {
	
	/**
	 * Returns a owner with the given id
	 * 
	 * @param id
	 * @return OwnerDTO
	 */
	public OwnerDTO findOwnerById(int id); 
}
