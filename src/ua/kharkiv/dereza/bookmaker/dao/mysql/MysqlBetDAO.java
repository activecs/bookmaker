package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.BetDAO;
import ua.kharkiv.dereza.bookmaker.dto.BetDTO;

/**
 * Implementation BetDAO for mysql db.
 * @author Eduard
 *
 */
public class MysqlBetDAO implements BetDAO {

	private static final Logger log = Logger.getLogger(MysqlBetDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_BETS_BY_CLIENT_ID = "SELECT * FROM bets WHERE client_id=?;";
	private static final String SQL_FIND_BETS_BY_TRIAL_ID_HORSE_ID = "SELECT bets.id, bets.client_id, bets.trial_horse_id, bets.value"
			+ " FROM trial_horse INNER JOIN bets ON bets.trial_horse_id=trial_horse.id WHERE trial_horse.trial_id=? AND trial_horse.horse_id=?;";
	private static final String SQL_FIND_BETS_BY_TRIAL_ID = "SELECT bets.id, bets.client_id, bets.trial_horse_id, bets.value"
			+ "FROM bets INNER JOIN trial_horse ON bets.trial_horse_id=trial_horse.id WHERE trial_horse.trial_id=?;";
	private static final String SQL_FIND_BETS_BY_ID = "SELECT * FROM bets WHERE id=?";
	private static final String SQL_CREATE_BET = "INSERT INTO bets(client_id, trial_horse_id, value) VALUES(?,?,?);";
	
	@Override
	public List<BetDTO> findBetsByClientId(int clientId) {
		BetDTO betDTO = null;
		LinkedList<BetDTO> list = new LinkedList<BetDTO>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			ps = conn.prepareStatement(SQL_FIND_BETS_BY_CLIENT_ID);
			ps.setInt(1, clientId);
			rs = ps.executeQuery();
			while (rs.next()) {
				betDTO = extractBetDTO(rs);
				list.add(betDTO);
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
	public List<BetDTO> findBetsByTrialIdHorseId(int trialId, int horseId) {
		BetDTO betDTO = null;
		LinkedList<BetDTO> list = new LinkedList<BetDTO>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			ps = conn.prepareStatement(SQL_FIND_BETS_BY_TRIAL_ID_HORSE_ID);
			ps.setInt(1, trialId);
			ps.setInt(2, horseId);
			rs = ps.executeQuery();
			while (rs.next()) {
				betDTO = extractBetDTO(rs);
				list.add(betDTO);
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
	public List<BetDTO> findBetsByTrialId(int trialId) {
		BetDTO betDTO = null;
		LinkedList<BetDTO> list = new LinkedList<BetDTO>();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			ps = conn.prepareStatement(SQL_FIND_BETS_BY_TRIAL_ID);
			ps.setInt(1, trialId);
			rs = ps.executeQuery();
			while (rs.next()) {
				betDTO = extractBetDTO(rs);
				list.add(betDTO);
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
	public BetDTO findBetById(int betId) {
		BetDTO betDTO = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			ps = conn.prepareStatement(SQL_FIND_BETS_BY_ID);
			ps.setInt(1, betId);
			rs = ps.executeQuery();
			betDTO = extractBetDTO(rs);
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, ps, conn);
		}
		return betDTO;
	}

	@Override
	public BetDTO extractBetDTO(ResultSet rs) throws SQLException {
		BetDTO betDTO = new BetDTO();
		betDTO.setId(rs.getInt(1));
		betDTO.setClientId(rs.getInt(2));
		betDTO.setTrialHoseId(rs.getInt(3));
		betDTO.setValue(rs.getBigDecimal(4));
		return betDTO;
	}

	@Override
	public void createBet(BetDTO betDTO) {
		PreparedStatement pstmt = null;
		try{
		conn = MysqlDAOFactory.createConnection();
		pstmt = conn.prepareStatement(SQL_CREATE_BET);
		pstmt.setInt(1, betDTO.getClientId());
		pstmt.setInt(2, betDTO.getTrialHoseId());
		pstmt.setBigDecimal(3, betDTO.getValue());
		pstmt.executeUpdate();
		}catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}
	}
}
