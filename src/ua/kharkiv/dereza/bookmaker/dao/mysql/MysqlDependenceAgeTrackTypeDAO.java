package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.DependenceAgeTrackTypeDAO;
import ua.kharkiv.dereza.bookmaker.dto.DependenceAgeTrackTypeDTO;

/**
 * Implementation DependenceAgeTrackTypeDAO for mysql db.
 * 
 * @author Eduard
 * 
 */
public class MysqlDependenceAgeTrackTypeDAO implements
		DependenceAgeTrackTypeDAO {

	private static final Logger log = Logger
			.getLogger(MysqlDependenceAgeTrackTypeDAO.class);

	private Connection conn = null;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_DEPENDENCE_BY_AGE_TRACK_TYPE_ID = "SELECT * FROM dependence_age_track_type WHERE age=? AND track_type_id=?;";

	@Override
	public DependenceAgeTrackTypeDTO findDependenceByAgeTrackTypeId(int age,
			int trackTypeId) {
		DependenceAgeTrackTypeDTO dependenceAgeTrackTypeDTO = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_DEPENDENCE_BY_AGE_TRACK_TYPE_ID);
			pstmt.setInt(1, age);
			pstmt.setInt(2, trackTypeId);
			rs = pstmt.executeQuery();
			dependenceAgeTrackTypeDTO = new DependenceAgeTrackTypeDTO();
			if (rs.next()) {
				dependenceAgeTrackTypeDTO.setId(rs.getInt(1));
				dependenceAgeTrackTypeDTO.setAge(rs.getInt(2));
				dependenceAgeTrackTypeDTO.setTrackTypeId(rs.getInt(3));
				dependenceAgeTrackTypeDTO.setChance(rs.getInt(4));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return dependenceAgeTrackTypeDTO;
	}
}