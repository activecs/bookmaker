package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.DependenceAgeDistanceDAO;
import ua.kharkiv.dereza.bookmaker.dto.DependenceAgeDistanceDTO;

/**
 * Implementation DependenceAgeDistanceDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlDependenceAgeDistanceDAO implements DependenceAgeDistanceDAO{
	
	private final static Logger log = Logger.getLogger(MysqlDependenceAgeDistanceDAO.class);
	
	Connection conn = null;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_DEPENDENCE_BY_AGE_DISTANCE_ID = "SELECT * FROM dependence_age_distance WHERE age=? AND distance_id=?;";

	@Override
	public DependenceAgeDistanceDTO findDependenceByAgeDistanceId(int age, int distanceId) {
		DependenceAgeDistanceDTO dependenceAgeDistanceDTO = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_DEPENDENCE_BY_AGE_DISTANCE_ID);
			pstmt.setInt(1, age);
			pstmt.setInt(2, distanceId);
			rs = pstmt.executeQuery();
			dependenceAgeDistanceDTO = new DependenceAgeDistanceDTO();
			if (rs.next()) {
				dependenceAgeDistanceDTO.setId(rs.getInt(1));
				dependenceAgeDistanceDTO.setAge(rs.getInt(2));
				dependenceAgeDistanceDTO.setDistanceId(rs.getInt(3));
				dependenceAgeDistanceDTO.setChance(rs.getInt(4));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return dependenceAgeDistanceDTO;
	}

}
