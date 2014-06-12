package ua.kharkiv.dereza.bookmaker.dao;

import ua.kharkiv.dereza.bookmaker.dto.TrackTypeDTO;

/**
 * Basic interface for all TrackTypeDAO
 * 
 * @author Eduard
 *
 */
public interface TrackTypeDAO {
	
	/**
	 * Returns a track type with the given id
	 * 
	 * @return TrackTypeDTO
	 */
	public TrackTypeDTO findTrackTypeById(int id);
}
