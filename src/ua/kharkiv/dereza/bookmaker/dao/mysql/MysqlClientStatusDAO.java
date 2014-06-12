package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.ClientStatusDAO;
import ua.kharkiv.dereza.bookmaker.dto.ClientStatusDTO;

/**
 * Implementation ClientStatusDAO for mysql db.
 * 
 * @author Eduard
 * 
 */
public class MysqlClientStatusDAO implements ClientStatusDAO {

	private static final Logger log = Logger
			.getLogger(MysqlClientStatusDAO.class);

	private Connection conn = null;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_CLIENT_STATUS_BY_ID = "SELECT * FROM client_statuses WHERE id=?;";
	private static final String SQL_FIND_CLIENT_STATUS_BY_STATUS_NAME = "SELECT * FROM client_statuses WHERE name=?;";

	@Override
	public ClientStatusDTO findClientStatusById(int id) {
		ClientStatusDTO clientStatusDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_CLIENT_STATUS_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			clientStatusDTO = new ClientStatusDTO();
			if (rs.next()) {
				clientStatusDTO.setId(rs.getInt(1));
				clientStatusDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return clientStatusDTO;
	}

	@Override
	public ClientStatusDTO findClientStatusByName(String name) {
		ClientStatusDTO clientStatusDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn
					.prepareStatement(SQL_FIND_CLIENT_STATUS_BY_STATUS_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			clientStatusDTO = new ClientStatusDTO();
			if (rs.next()) {
				clientStatusDTO.setId(rs.getInt(1));
				clientStatusDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return clientStatusDTO;
	}

}