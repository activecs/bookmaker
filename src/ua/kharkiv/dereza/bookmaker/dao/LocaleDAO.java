package ua.kharkiv.dereza.bookmaker.dao;

import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.LocaleDTO;

/**
 * Basic interface for all LocaleDAO
 * 
 * @author Eduard
 *
 */
public interface LocaleDAO {
	
	/**
	 * Returns a locale with the given id
	 * 
	 * @param locale id
	 *      
	 * @return LocaleDTO
	 */
	public LocaleDTO findLocaleById(int id);
	
	/**
	 * Returns a list of locales
	 * 
	 * @return List<LocaleDTO>
	 */
	public List<LocaleDTO> findAllLocales();
	
	/**
	 * Returns a id with the given locale name
	 * 
	 * @param locale name
	 *            
	 * @return LocaleDTO
	 */
	public LocaleDTO findLocaleByName(String name);
}
