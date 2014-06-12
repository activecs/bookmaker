package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.CoverDAO;
import ua.kharkiv.dereza.bookmaker.dto.CoverDTO;

/**
 * Implementation CoverDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlCoverDAO implements CoverDAO {

	private static final Logger log = Logger.getLogger(MysqlCoverDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_COVERS_BY_ID = "SELECT * FROM covers WHERE id=?;";
	private static final String SQL_FIND_COVERS_BY_NAME = "SELECT * FROM covers WHERE name=?;";

	@Override
	public CoverDTO findCoverById(int id) {
		CoverDTO coverDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_COVERS_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			coverDTO = new CoverDTO();
			if (rs.next()) {
				coverDTO.setId(rs.getInt(1));
				coverDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return coverDTO;
	}

	@Override
	public CoverDTO findCoverByName(String name) {
		CoverDTO coverDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_COVERS_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			coverDTO = new CoverDTO();
			if (rs.next()) {
				coverDTO.setId(rs.getInt(1));
				coverDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return coverDTO;
	}
}