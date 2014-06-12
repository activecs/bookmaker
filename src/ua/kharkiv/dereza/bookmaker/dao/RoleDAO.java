package ua.kharkiv.dereza.bookmaker.dao;

import ua.kharkiv.dereza.bookmaker.dto.RoleDTO;

/**
 * Basic interface for all RoleDAO
 * 
 * @author Eduard
 *
 */
public interface RoleDAO {
	
	/**
	 * Returns a role with the given id
	 * 
	 * @param role id
	 * @return RoleDTO
	 */
	public RoleDTO findRoleById(int id);
	
	/**
	 * Returns a role with the given name
	 * 
	 * @param role name
	 * @return RoleDTO
	 */
	public RoleDTO findRoleByName(String name);
}
