package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.HorseStatusDAO;
import ua.kharkiv.dereza.bookmaker.dto.HorseStatusDTO;

/**
 * Implementation HorseStatusDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlHorseStatusDAO implements HorseStatusDAO {

	private Logger log = Logger.getLogger(MysqlHorseStatusDAO.class);

	private Connection conn = null;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_HORSE_STATUS_BY_ID = "SELECT * FROM horse_statuses WHERE id=?;";
	private static final String SQL_FIND_HORSE_STATUS_BY_STATUS_NAME = "SELECT * FROM horse_statuses WHERE name=?;";
	private static final String SQL_FIND_ALL_HORSE_STATUS = "SELECT * FROM horse_statuses;";
	
	@Override
	public HorseStatusDTO findHorseStatusById(int id) {
		HorseStatusDTO horseStatusDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_HORSE_STATUS_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			horseStatusDTO = new HorseStatusDTO();
			if (rs.next()) {
				horseStatusDTO.setId(rs.getInt(1));
				horseStatusDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return horseStatusDTO;
	}

	@Override
	public HorseStatusDTO findHorseStatusByName(String name) {
		HorseStatusDTO horseStatusDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_HORSE_STATUS_BY_STATUS_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			horseStatusDTO = new HorseStatusDTO();
			if (rs.next()) {
				horseStatusDTO.setId(rs.getInt(1));
				horseStatusDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return horseStatusDTO;
	}

	@Override
	public List<HorseStatusDTO> finfAllHorseStatus() {
		HorseStatusDTO horseStatusDTO = null;
		List<HorseStatusDTO> list = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_ALL_HORSE_STATUS);
			list = new LinkedList<HorseStatusDTO>();
			rs = pstmt.executeQuery();
			while(rs.next()){
				horseStatusDTO = new HorseStatusDTO();
				horseStatusDTO.setId(rs.getInt(1));
				horseStatusDTO.setName(rs.getString(2));
				list.add(horseStatusDTO);
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