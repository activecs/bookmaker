package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.RoleDAO;
import ua.kharkiv.dereza.bookmaker.dto.RoleDTO;

/**
 * Implementation RoleDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlRoleDAO implements RoleDAO{
	
	private static final Logger log = Logger.getLogger(MysqlLocaleDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_ROLE_BY_ID = "SELECT * FROM roles WHERE id=?";
	private static final String SQL_FIND_ROLE_BY_NAME = "SELECT * FROM roles WHERE name=?";
	
	@Override
	public RoleDTO findRoleById(int id) {
		RoleDTO role = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_ROLE_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			role = new RoleDTO();
			if (rs.next()) {
				role.setId(rs.getInt(1));
				role.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return role;
	}

	@Override
	public RoleDTO findRoleByName(String name) {
		RoleDTO role = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_ROLE_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			role = new RoleDTO();
			if (rs.next()) {
				role.setId(rs.getInt(1));
				role.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return role;
	}
	
	
}