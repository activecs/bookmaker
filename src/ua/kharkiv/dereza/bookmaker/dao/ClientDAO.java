package ua.kharkiv.dereza.bookmaker.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;

/**
 * Basic interface for all ClientDAO
 * 
 * @author Eduard
 *
 */
public interface ClientDAO {
	
	/**
	 * Returns a client with the given login
	 * 
	 * @param login
	 * @return ClientDTO
	 */
	public ClientDTO findClientByLogin(String login);
	
	/**
	 * Returns a client with the given id
	 * 
	 * @param client id
	 * @return ClientDTO
	 */
	public ClientDTO findClientById(int id);
	
	/**
	 * Extracts ClientDTO from given ResultSet
	 * 
	 * @param rs
	 * @return ClientDTO
	 * @throws SQLException
	 */
	public ClientDTO extractClientDTO(ResultSet rs) throws SQLException;
	
	/**
	 * Creates new client's account
	 * 
	 * @param clientDTO
	 */
	public void createClient(ClientDTO clientDTO);
	
	/**
	 * Updates information about client
	 * 
	 * @param clientDTO
	 */
	public void updateClient(ClientDTO clientDTO);
	
	/**
	 * Returns all clients
	 * 
	 * @return List<ClientDTO>
	 */
	public List<ClientDTO> findAllClients();
	
	/**
	 * Deletes clients with given id
	 * 
	 * @param clientId
	 */
	public void deleteClient(int clientId);
}
