package ua.kharkiv.dereza.bookmaker.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ua.kharkiv.dereza.bookmaker.dto.TrackDTO;

/**
 * Basic interface for all TrackDAO
 * 
 * @author Eduard
 *
 */
public interface TrackDAO {
	
	/**
	 * Returns a track with the given id
	 * 
	 * @param id
	 * @return TrackDTO
	 */
	public TrackDTO findTrackById(int id);
	
	/**
	 * Returns all tracks
	 * 
	 * @return List<TrackDTO>
	 */
	public List<TrackDTO> findAllTracks();
	
	/**
	 * Updates information about track 
	 * 
	 * @param trackDTO
	 */
	public void updateTrack(TrackDTO trackDTO);
	
	/**
	 * Extract TrackDTO from resultSet
	 * 
	 * @param rs
	 * @return TrackDTO
	 */
	public TrackDTO extracktTrackDTO(ResultSet rs) throws SQLException;
}
