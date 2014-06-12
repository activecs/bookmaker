package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.TrialStatusDAO;
import ua.kharkiv.dereza.bookmaker.dto.TrialStatusDTO;

/**
 * Implementation TrialStatusDAO for mysql db.
 * 
 * @author Eduard
 * 
 */
public class MysqlTrialStatusDAO implements TrialStatusDAO {

	private static final Logger log = Logger
			.getLogger(MysqlTrialStatusDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_TRIAL_STATUS_BY_ID = "SELECT * FROM trial_statuses WHERE id=?;";
	private static final String SQL_FIND_TRIAL_STATUS_BY_STATUS_NAME = "SELECT * FROM trial_statuses WHERE name=?;";
	private static final String SQL_FIND_ALL_TRIAL_STATUSES = "SELECT * FROM trial_statuses;";

	@Override
	public TrialStatusDTO findTrialStatusById(int id) {
		TrialStatusDTO trialStatusDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_TRIAL_STATUS_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			trialStatusDTO = new TrialStatusDTO();
			if (rs.next()) {
				trialStatusDTO.setId(rs.getInt(1));
				trialStatusDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return trialStatusDTO;
	}

	@Override
	public TrialStatusDTO findTrialStatusByName(String name) {
		TrialStatusDTO trialStatusDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_TRIAL_STATUS_BY_STATUS_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			trialStatusDTO = new TrialStatusDTO();
			if (rs.next()) {
				trialStatusDTO.setId(rs.getInt(1));
				trialStatusDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return trialStatusDTO;
	}

	@Override
	public List<TrialStatusDTO> findAllTrialStatuses() {
		TrialStatusDTO trialStatusDTO = null;
		List<TrialStatusDTO> list = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_ALL_TRIAL_STATUSES);
			rs = pstmt.executeQuery();
			list = new LinkedList<TrialStatusDTO>();
			while (rs.next()) {
				trialStatusDTO = new TrialStatusDTO();
				trialStatusDTO.setId(rs.getInt(1));
				trialStatusDTO.setName(rs.getString(2));
				list.add(trialStatusDTO);
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
