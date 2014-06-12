package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dto.ClientDTO;

/**
 * Implementation ClientDAO for mysql db.
 * 
 * @author Eduard
 * 
 */
public class MysqlClientDAO implements ClientDAO {

	private static final Logger log = Logger.getLogger(MysqlClientDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_CLIENT_BY_LOGIN = "SELECT * FROM clients WHERE login=?;";
	private static final String SQL_FIND_CLIENT_BY_ID = "SELECT * FROM clients WHERE id=?;";
	private static final String SQL_CREATE_NEW_CLIENT = "INSERT INTO clients(login, password, surname, name, email) VALUES(?,?,?,?,?);";
	private static final String SQL_UPDATE_CLIENT_INFO = "UPDATE clients SET login=?, password=?, surname=?, name=?, email=?, balance=?, role_id=?, locale_id=?, client_status_id=? WHERE id=?;";
	private static final String SQL_FIND_ALL_CLIENTS = "SELECT * FROM clients;";
	private static final String SQL_DELETE_CLIENT = "DELETE FROM clients WHERE id=?";

	@Override
	public ClientDTO findClientByLogin(String login) {
		ClientDTO client = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_CLIENT_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				client = extractClientDTO(rs);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return client;
	}

	@Override
	public ClientDTO findClientById(int id) {
		ClientDTO client = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_CLIENT_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				client = extractClientDTO(rs);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return client;
	}

	@Override
	public void createClient(ClientDTO clientDTO) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_CREATE_NEW_CLIENT);
			pstmt.setString(1, clientDTO.getLogin());
			pstmt.setString(2, clientDTO.getPassword());
			pstmt.setString(3, clientDTO.getSurname());
			pstmt.setString(4, clientDTO.getName());
			pstmt.setString(5, clientDTO.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
			throw new RuntimeException("Cannot do query", ex);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}
	}

	@Override
	public void updateClient(ClientDTO clientDTO) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE_CLIENT_INFO);
			pstmt.setString(1, clientDTO.getLogin());
			pstmt.setString(2, clientDTO.getPassword());
			pstmt.setString(3, clientDTO.getSurname());
			pstmt.setString(4, clientDTO.getName());
			pstmt.setString(5, clientDTO.getEmail());
			pstmt.setBigDecimal(6, clientDTO.getBalance());
			pstmt.setInt(7, clientDTO.getRoleId());
			pstmt.setInt(8, clientDTO.getLocaleId());
			pstmt.setInt(9, clientDTO.getClientStatusId());
			pstmt.setInt(10, clientDTO.getId());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}
	}

	@Override
	public ClientDTO extractClientDTO(ResultSet rs) throws SQLException {
		ClientDTO client = new ClientDTO();
		client.setId(rs.getInt(1));
		client.setLogin(rs.getString(2));
		client.setPassword(rs.getString(3));
		client.setSurname(rs.getString(4));
		client.setName(rs.getString(5));
		client.setEmail(rs.getString(6));
		client.setBalance(rs.getBigDecimal(7));
		client.setRoleId(rs.getInt(8));
		client.setLocaleId(rs.getInt(9));
		client.setClientStatusId(rs.getInt(10));
		return client;
	}

	@Override
	public List<ClientDTO> findAllClients() {
		ClientDTO client = null;
		List<ClientDTO> list = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			list = new LinkedList<ClientDTO>();
			pstmt = conn.prepareStatement(SQL_FIND_ALL_CLIENTS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				client = extractClientDTO(rs);
				list.add(client);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public void deleteClient(int clientId) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_DELETE_CLIENT);
			pstmt.setInt(1, clientId);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}
	}

}