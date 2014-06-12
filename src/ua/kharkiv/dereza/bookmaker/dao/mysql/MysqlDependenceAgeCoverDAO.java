package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.DependenceAgeCoverDAO;
import ua.kharkiv.dereza.bookmaker.dto.DependenceAgeCoverDTO;

/**
 * Implementation DependenceAgeCoverDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlDependenceAgeCoverDAO implements DependenceAgeCoverDAO {

	private final static Logger log = Logger
			.getLogger(MysqlDependenceAgeCoverDAO.class);

	Connection conn = null;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_DEPENDENCE_BY_AGE_COVER_ID = "SELECT * FROM dependence_age_cover WHERE age=? AND cover_id=?;";

	@Override
	public DependenceAgeCoverDTO findDependenceByAgeCover(int age, int coverId) {
		DependenceAgeCoverDTO dependenceAgeCoverDTO = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_DEPENDENCE_BY_AGE_COVER_ID);
			pstmt.setInt(1, age);
			pstmt.setInt(2, coverId);
			rs = pstmt.executeQuery();
			dependenceAgeCoverDTO = new DependenceAgeCoverDTO();
			if (rs.next()) {
				dependenceAgeCoverDTO.setId(rs.getInt(1));
				dependenceAgeCoverDTO.setAge(rs.getInt(2));
				dependenceAgeCoverDTO.setCoverId(rs.getInt(3));
				dependenceAgeCoverDTO.setChance(rs.getInt(4));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return dependenceAgeCoverDTO;
	}
}