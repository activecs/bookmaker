package ua.kharkiv.dereza.bookmaker.dao;

import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.HorseStatusDTO;

/**
 * Basic interface for all HorseStatusDAO
 * 
 * @author Eduard
 *
 */
public interface HorseStatusDAO {
	
	/**
	 * Returns a status with the given id
	 * 
	 * @param status id
	 * @return HorseStatusDTO
	 */
	public HorseStatusDTO findHorseStatusById(int id);
	
	/**
	 * Returns a HorseStatusDTO with the given status name
	 * 
	 * @param status name
	 * @return HorseStatusDTO
	 */
	public HorseStatusDTO findHorseStatusByName(String name);
	
	/**
	 * Returns all horse statuses
	 * 
	 * @return List<HorseStatusDTO>
	 */
	public List<HorseStatusDTO> finfAllHorseStatus();
}
