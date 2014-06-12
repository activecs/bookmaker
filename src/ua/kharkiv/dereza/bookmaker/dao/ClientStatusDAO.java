package ua.kharkiv.dereza.bookmaker.dao;

import ua.kharkiv.dereza.bookmaker.dto.ClientStatusDTO;

/**
 * Basic interface for all ClientStatusDAO
 * 
 * @author Eduard
 *
 */
public interface ClientStatusDAO {
	
	/**
	 * Returns a status with the given id
	 * 
	 * @param status id
	 * @return ClientStatusDTO
	 */
	public ClientStatusDTO findClientStatusById(int id);
	
	/**
	 * Returns status with the given status name
	 * 
	 * @param status name
	 * @return ClientStatusDTO
	 */
	public ClientStatusDTO findClientStatusByName(String name);
	
}
