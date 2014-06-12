package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dto.TrialHorseDTO;

/**
 * Implementation TrialHorseDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlTrialHorseDAO implements TrialHorseDAO {

	private static final Logger log = Logger.getLogger(MysqlTrialHorseDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_TRIAL_HORSE_BY_ID = "SELECT * FROM trial_horse WHERE id=?";
	private static final String SQL_FIND_TRIAL_HORSE_BY_TRIAL_HORSE_ID = "SELECT * FROM trial_horse WHERE trial_id=? AND horse_id=?;";
	private static final String SQL_FIND_TRIAL_HORSE_BY_TRIAL_ID = "SELECT * FROM trial_horse WHERE trial_id=?;";
	private static final String SQL_FIND_TRIAL_HORSE_BY_HORSE_ID = "SELECT * FROM trial_horse WHERE horse_id=?;";
	private static final String SQL_DELETE_TRIAL_HORSE_BY_TRIAL_HORSE_ID = "DELETE FROM trial_horse WHERE trial_id=? AND horse_id=?;";
	private static final String SQL_ADD_HORSE_TO_TRIAL = "INSERT INTO trial_horse(trial_id, horse_id, horse_status_id) VALUES(?, ?, ?);";
	private static final String SQL_UPDATE_TRIAL_HORSE_INFO = "UPDATE trial_horse SET win_coefficient=?, horse_status_id=?, place=?  WHERE trial_id=? AND horse_id=?;";
	
	@Override
	public TrialHorseDTO findTrialHorseByTrialIdHorseId(int trialId, int horseId) {
		TrialHorseDTO trialHorseDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_TRIAL_HORSE_BY_TRIAL_HORSE_ID);
			pstmt.setInt(1, trialId);
			pstmt.setInt(2, horseId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				trialHorseDTO = extractTrialHorseDTO(rs);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return trialHorseDTO;
	}

	@Override
	public void addHorseToTrial(TrialHorseDTO trialHorseDTO) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_ADD_HORSE_TO_TRIAL);
			pstmt.setInt(1, trialHorseDTO.getTrialId());
			pstmt.setInt(2, trialHorseDTO.getHorseId());
			pstmt.setInt(3, trialHorseDTO.getHorseStatusId());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}
	}
	
	@Override
	public void deleteTrialHorseByTrialIdHorseId(int trialId, int horseId) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_DELETE_TRIAL_HORSE_BY_TRIAL_HORSE_ID);
			pstmt.setInt(1, trialId);
			pstmt.setInt(2, horseId);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}
	}

	@Override
	public void updateTrialHorseInfo(TrialHorseDTO trialHorseDTO) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE_TRIAL_HORSE_INFO);
			pstmt.setBigDecimal(1 ,trialHorseDTO.getWinCoefficient());
			pstmt.setInt(2, trialHorseDTO.getHorseStatusId());
			pstmt.setInt(3, trialHorseDTO.getPlace());
			pstmt.setInt(4, trialHorseDTO.getTrialId());
			pstmt.setInt(5, trialHorseDTO.getHorseId());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}
	}

	@Override
	public List<TrialHorseDTO> findTrialHorsesByTrialId(int trialId) {
		TrialHorseDTO trialHorseDTO = null;
		LinkedList<TrialHorseDTO> list = new LinkedList<TrialHorseDTO>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			ps = conn.prepareStatement(SQL_FIND_TRIAL_HORSE_BY_TRIAL_ID);
			ps.setInt(1, trialId);
			rs = ps.executeQuery();
			while (rs.next()) {
				trialHorseDTO = extractTrialHorseDTO(rs);
				list.add(trialHorseDTO);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, ps, conn);
		}
		return list;
	}
	
	@Override
	public List<TrialHorseDTO> findTrialHorsesByHorseId(int horseId) {
		TrialHorseDTO trialHorseDTO = null;
		LinkedList<TrialHorseDTO> list = new LinkedList<TrialHorseDTO>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			ps = conn.prepareStatement(SQL_FIND_TRIAL_HORSE_BY_HORSE_ID);
			ps.setInt(1, horseId);
			rs = ps.executeQuery();
			while (rs.next()) {
				trialHorseDTO = extractTrialHorseDTO(rs);
				list.add(trialHorseDTO);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, ps, conn);
		}
		return list;
	}

	@Override
	public TrialHorseDTO extractTrialHorseDTO(ResultSet rs) throws SQLException {
		TrialHorseDTO trialHorseDTO = new TrialHorseDTO();
		trialHorseDTO.setId(rs.getInt(1));
		trialHorseDTO.setTrialId(rs.getInt(2));
		trialHorseDTO.setHorseId(rs.getInt(3));
		trialHorseDTO.setPlace(rs.getInt(4));
		trialHorseDTO.setWinCoefficient(rs.getBigDecimal(5));
		trialHorseDTO.setHorseStatusId(rs.getInt(6));
		return trialHorseDTO;
	}

	@Override
	public TrialHorseDTO findTrialHorseById(int id) {
		TrialHorseDTO trialHorseDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_TRIAL_HORSE_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				trialHorseDTO = extractTrialHorseDTO(rs);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return trialHorseDTO;
	}

}