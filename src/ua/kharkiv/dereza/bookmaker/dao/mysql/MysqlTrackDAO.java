package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.TrackDAO;
import ua.kharkiv.dereza.bookmaker.dto.TrackDTO;

/**
 * Implementation TrackDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlTrackDAO implements TrackDAO{
	
	private static final Logger log = Logger.getLogger(MysqlTrackDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_TRACK_BY_ID = "SELECT * FROM tracks WHERE id=?;";
	private static final String SQL_FIND_ALL_TRACKS = "SELECT * FROM tracks;";
	private static final String SQL_UPDATE_TRACK_INFO = "UPDATE tracks SET name=?, country=?, cover_id=?, track_type_id=? WHERE id=?;";
	
	@Override
	public TrackDTO findTrackById(int id) {
		TrackDTO trackDTO = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_TRACK_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				trackDTO = extracktTrackDTO(rs);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return trackDTO;
	}

	@Override
	public List<TrackDTO> findAllTracks() {
		TrackDTO trackDTO = null;
		List<TrackDTO> list = null; 
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_ALL_TRACKS);
			rs = pstmt.executeQuery();
			list = new LinkedList<TrackDTO>();
			while(rs.next()){
				trackDTO = extracktTrackDTO(rs);
				list.add(trackDTO);
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
	public void updateTrack(TrackDTO trackDTO) {
		PreparedStatement pstmt = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE_TRACK_INFO);
			pstmt.setString(1, trackDTO.getName());
			pstmt.setString(2, trackDTO.getCountry());
			pstmt.setInt(3, trackDTO.getCoverId());
			pstmt.setInt(4, trackDTO.getTrackTypeId());
			pstmt.setInt(5, trackDTO.getId());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(null, pstmt, conn);
		}
	}

	@Override
	public TrackDTO extracktTrackDTO(ResultSet rs) throws SQLException {
		TrackDTO trackDTO = new TrackDTO();
		trackDTO.setId(rs.getInt(1));
		trackDTO.setName(rs.getString(2));
		trackDTO.setCountry(rs.getString(3));
		trackDTO.setCoverId(rs.getInt(4));
		trackDTO.setTrackTypeId(rs.getInt(5));
		return trackDTO;
	}
	
}
