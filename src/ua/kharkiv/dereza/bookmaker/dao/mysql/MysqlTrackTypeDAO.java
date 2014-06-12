package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.TrackTypeDAO;
import ua.kharkiv.dereza.bookmaker.dto.TrackTypeDTO;

/**
 * Implementation TrackTypeDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlTrackTypeDAO implements TrackTypeDAO {

	private static final Logger log = Logger.getLogger(MysqlTrackTypeDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_TRACK_BY_ID = "SELECT * FROM track_types WHERE id=?";

	@Override
	public TrackTypeDTO findTrackTypeById(int id) {
		TrackTypeDTO trackTypeDTO = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_TRACK_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			trackTypeDTO = new TrackTypeDTO();
			if (rs.next()) {
				trackTypeDTO.setId(rs.getInt(1));
				trackTypeDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return trackTypeDTO;
	}
}