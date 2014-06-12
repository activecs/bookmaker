package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dto.TrialDTO;

/**
 * Implementation TrialDAO for mysql db.
 * 
 * @author Eduard
 * 
 */
public class MysqlTrialDAO implements TrialDAO {

	private static final Logger log = Logger.getLogger(MysqlTrialDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_TRIAL_BY_ID = "SELECT * FROM trials WHERE id=?;";
	private static final String SQL_FIND_TRIALS_BY_STATUS_ID = "SELECT * FROM trials WHERE trial_status_id=?;";
	private static final String SQL_UPDATE_TRIAL_INFO = "UPDATE trials SET track_id=?, distance_id=?, start_time=?, trial_status_id=? WHERE id=?;";
	private static final String SQL_CREATE_NEW_TRIAL = "INSERT INTO trials(track_id, distance_id, start_time, trial_status_id) VALUES(?,?,?,?);";
	private static final String SQL_DELETE_TRIAL_BY_ID = "DELETE FROM trials WHERE id=?;";
	
	@Override
	public TrialDTO findTrialById(int id) {
		TrialDTO trialDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_TRIAL_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				trialDTO = new TrialDTO();
				trialDTO.setId(rs.getInt(1));
				trialDTO.setTrackId(rs.getInt(2));
				trialDTO.setDistanceId(rs.getInt(3));
				trialDTO.setStartTime(rs.getTimestamp(4));
				trialDTO.setTrialStatusId(rs.getInt(5));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return trialDTO;
	}

	@Override
	public List<TrialDTO> findTrialsByStatusId(int statusId) {
		List<TrialDTO> list = null;
		TrialDTO trialDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_TRIALS_BY_STATUS_ID);
			pstmt.setInt(1, statusId);
			rs = pstmt.executeQuery();
			list = new LinkedList<TrialDTO>();
			while (rs.next()) {
				trialDTO = new TrialDTO();
				trialDTO.setId(rs.getInt(1));
				trialDTO.setTrackId(rs.getInt(2));
				trialDTO.setDistanceId(rs.getInt(3));
				trialDTO.setStartTime(rs.getTimestamp(4));
				trialDTO.setTrialStatusId(rs.getInt(5));
				list.add(trialDTO);
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
	public void updateTrial(TrialDTO trialDTO) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE_TRIAL_INFO);
			pstmt.setInt(1, trialDTO.getTrackId());
			pstmt.setInt(2, trialDTO.getDistanceId());
			pstmt.setTimestamp(3, new Timestamp(trialDTO.getStartTime()
					.getTime()));
			pstmt.setInt(4, trialDTO.getTrialStatusId());
			pstmt.setInt(5, trialDTO.getId());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}

	}

	@Override
	public void createTrial(TrialDTO trialDTO) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_CREATE_NEW_TRIAL);
			pstmt.setInt(1,trialDTO.getTrackId());
			pstmt.setInt(2,trialDTO.getDistanceId());
			pstmt.setTimestamp(3, new Timestamp(trialDTO.getStartTime()
					.getTime()));
			pstmt.setInt(4,trialDTO.getTrialStatusId());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}

	}

	@Override
	public void deleteTrialById(int id) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_DELETE_TRIAL_BY_ID);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}
		
	}
	
}
