package ua.kharkiv.dereza.bookmaker.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.HorseDTO;

/**
 * Basic interface for all HorseDAO
 * 
 * @author Eduard
 *
 */
public interface HorseDAO {
	
	/**
	 * Returns a horse with the given id
	 * 
	 * @param id
	 * @return HorseDTO
	 */
	public HorseDTO findHorseById(int id);
	
	/**
	 * Returns a horse with the given owner id
	 * 
	 * @param ownerId
	 * @return
	 */
	public HorseDTO findHorseByOwnerId(int ownerId);
	
	/**
	 * Returns all horses
	 * 
	 * @return List<HorseDTO>
	 */
	public List<HorseDTO> findAllHorses();
	
	/**
	 * Extract HorseDTO from resultSet
	 * 
	 * @param rs
	 * @return HorseDTO
	 */
	public HorseDTO extractHorseDTO(ResultSet rs) throws SQLException;
}
