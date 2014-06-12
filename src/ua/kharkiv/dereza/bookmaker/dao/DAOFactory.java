package ua.kharkiv.dereza.bookmaker.dao;

import ua.kharkiv.dereza.bookmaker.dao.mysql.MysqlDAOFactory;

/**
 * Abstract class DAO Factory
 * 
 * @author Eduard
 *
 */
public abstract class DAOFactory {

	public static final int MYSQL = 1;

	// In future, if you want change sql server you will create new factory.
	// For example:
	// public static final int DERBY = 2;

	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case MYSQL:
			return new MysqlDAOFactory();
			// It will be necessary if you change sql server.
			// case DERBY:
			// return new DerbyDAOFactory();
		default:
			return null;
		}
	}
	
	/**
	 * Abstract factory Method for BetDAO
	 * 
	 * @return BetDAO
	 */
	public abstract BetDAO getBetDAO();
	
	/**
	 * Abstract factory Method for ClientDAO
	 *  
	 * @return ClientDAO
	 */
	public abstract ClientDAO getClientDAO();
	
	/**
	 * Abstract factory Method for ClientStatusDAO
	 * 
	 * @return ClientStatusDAO
	 */
	public abstract ClientStatusDAO getClientStatusDAO();
	
	/**
	 * Abstract factory Method for CoverDAO
	 * 
	 * @return CoverDAO
	 */
	public abstract CoverDAO getCoverDAO();
	
	/**
	 * Abstract factory Method for DependenceAgeCoverDAO
	 * 
	 * @return DependenceAgeCoverDAO
	 */
	public abstract DependenceAgeCoverDAO getDependenceAgeCoverDAO();
	
	/**
	 * Abstract factory Method for DependenceAgeDistanceDAO
	 * 
	 * @return DependenceAgeDistanceDAO
	 */
	public abstract DependenceAgeDistanceDAO getDependenceAgeDistanceDAO();
	
	/**
	 * Abstract factory Method for DependenceAgeTrackTypeDAO
	 * 
	 * @return DependenceAgeTrackTypeDAO
	 */
	public abstract DependenceAgeTrackTypeDAO getDependenceAgeTrackTypeDAO();
	
	/**
	 * Abstract factory Method for DistanceDAO
	 * 
	 * @return DistanceDAO
	 */
	public abstract DistanceDAO getDistanceDAO();
	
	/**
	 * Abstract factory Method for HorseDAO
	 * 
	 * @return HorseDAO
	 */
	public abstract HorseDAO getHorseDAO();
	
	/**
	 * Abstract factory Method for HorseStatusDAO
	 * 
	 * @return HorseStatusDAO
	 */
	public abstract HorseStatusDAO getHorseStatusDAO();
	
	/**
	 * Abstract factory Method for LocaleDAO
	 * 
	 * @return LocaleDAO
	 */
	public abstract LocaleDAO getLocaleDAO();
	
	/**
	 * Abstract factory Method for OwnerDAO
	 * 
	 * @return OwnerDAO
	 */
	public abstract OwnerDAO getOwnerDAO();
	
	/**
	 * Abstract factory Method for RoleDAO
	 * 
	 * @return RoleDAO
	 */
	public abstract RoleDAO getRoleDAO();
	
	/**
	 * Abstract factory Method for TrackDAO
	 * 
	 * @return TrackDAO
	 */
	public abstract TrackDAO getTrackDAO();
	
	/**
	 * Abstract factory Method for TrackTypeDAO
	 * 
	 * @return TrackTypeDAO
	 */
	public abstract TrackTypeDAO getTrackTypeDAO();
	
	/**
	 * Abstract factory Method for TrialDAO
	 * 
	 * @return TrialDAO
	 */
	public abstract TrialDAO getTrialDAO();
	
	/**
	 * Abstract factory Method for TrialHorseDAO
	 * 
	 * @return TrialHorseDAO
	 */
	public abstract TrialHorseDAO getTrialHorseDAO();
	
	/**
	 * Abstract factory Method for TrialStatusDAO
	 * 
	 * @return TrialStatusDAO
	 */
	public abstract TrialStatusDAO getTrialStatusDAO();
}
