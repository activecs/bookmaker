package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.HorseDAO;
import ua.kharkiv.dereza.bookmaker.dto.HorseDTO;

/**
 * Implementation HorseDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlHorseDAO implements HorseDAO {

	private Logger log = Logger.getLogger(MysqlHorseDAO.class);

	private Connection conn = null;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_ALL_HORSES = "SELECT * FROM horses;";
	private static final String SQL_FIND_HORSES_BY_ID = "SELECT * FROM horses WHERE id=?;";
	private static final String SQL_FIND_HORSES_BY_OWNER_ID = "SELECT * FROM horses WHERE owner_id=?;";

	@Override
	public HorseDTO findHorseById(int id) {
		HorseDTO horseDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_HORSES_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				horseDTO = extractHorseDTO(rs);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return horseDTO;
	}

	@Override
	public HorseDTO findHorseByOwnerId(int ownerId) {
		HorseDTO horseDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_HORSES_BY_OWNER_ID);
			pstmt.setInt(1, ownerId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				horseDTO = extractHorseDTO(rs);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return horseDTO;
	}

	@Override
	public List<HorseDTO> findAllHorses() {
		HorseDTO horseDTO = null;
		LinkedList<HorseDTO> list = new LinkedList<HorseDTO>();

		Statement ps = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			ps = conn.createStatement();
			rs = ps.executeQuery(SQL_FIND_ALL_HORSES);
			while (rs.next()) {
				horseDTO = extractHorseDTO(rs);
				list.add(horseDTO);
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
	public HorseDTO extractHorseDTO(ResultSet rs) throws SQLException {
		HorseDTO horseDTO = new HorseDTO();
		horseDTO.setId(rs.getInt(1));
		horseDTO.setName(rs.getString(2));
		horseDTO.setBirthYear(rs.getInt(3));
		horseDTO.setColor(rs.getString(4));
		horseDTO.setWeight(rs.getInt(5));
		horseDTO.setOwnerId(rs.getInt(6));
		return horseDTO;
	}

}
