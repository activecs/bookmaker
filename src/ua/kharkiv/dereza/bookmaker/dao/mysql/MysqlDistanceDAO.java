package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.DistanceDAO;
import ua.kharkiv.dereza.bookmaker.dto.DistanceDTO;

/**
 * Implementation DistanceDAO for mysql db.
 * 
 * @author Eduard
 * 
 */
public class MysqlDistanceDAO implements DistanceDAO {

	private final static Logger log = Logger.getLogger(MysqlDistanceDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_DISTANCE_BY_ID = "SELECT * FROM distances WHERE id=?;";
	private static final String SQL_FIND_DISTANCE_BY_DISTANCE = "SELECT * FROM distances WHERE distance=?;";
	private static final String SQL_FIND_ALL_DISTANCES = "SELECT * FROM distances;";

	@Override
	public DistanceDTO findDistanceById(int id) {
		DistanceDTO distanceDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_DISTANCE_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			distanceDTO = new DistanceDTO();
			if (rs.next()) {
				distanceDTO.setId(rs.getInt(1));
				distanceDTO.setDistance(rs.getInt(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return distanceDTO;
	}

	@Override
	public DistanceDTO findDistanceByDistance(int distance) {
		DistanceDTO distanceDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_DISTANCE_BY_DISTANCE);
			pstmt.setInt(1, distance);
			rs = pstmt.executeQuery();
			distanceDTO = new DistanceDTO();
			if (rs.next()) {
				distanceDTO.setId(rs.getInt(1));
				distanceDTO.setDistance(rs.getInt(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return distanceDTO;
	}

	@Override
	public List<DistanceDTO> findAllDistances() {
		DistanceDTO distanceDTO = null;
		List<DistanceDTO> list = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_ALL_DISTANCES);
			rs = pstmt.executeQuery();
			list = new LinkedList<DistanceDTO>();
			while (rs.next()) {
				distanceDTO = new DistanceDTO();
				distanceDTO.setId(rs.getInt(1));
				distanceDTO.setDistance(rs.getInt(2));
				list.add(distanceDTO);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return list;
	}

}
