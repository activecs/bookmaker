package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.BetDAO;
import ua.kharkiv.dereza.bookmaker.dao.ClientDAO;
import ua.kharkiv.dereza.bookmaker.dao.ClientStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.CoverDAO;
import ua.kharkiv.dereza.bookmaker.dao.DAOFactory;
import ua.kharkiv.dereza.bookmaker.dao.DependenceAgeCoverDAO;
import ua.kharkiv.dereza.bookmaker.dao.DependenceAgeDistanceDAO;
import ua.kharkiv.dereza.bookmaker.dao.DependenceAgeTrackTypeDAO;
import ua.kharkiv.dereza.bookmaker.dao.DistanceDAO;
import ua.kharkiv.dereza.bookmaker.dao.HorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.HorseStatusDAO;
import ua.kharkiv.dereza.bookmaker.dao.LocaleDAO;
import ua.kharkiv.dereza.bookmaker.dao.OwnerDAO;
import ua.kharkiv.dereza.bookmaker.dao.RoleDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrackDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrackTypeDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialHorseDAO;
import ua.kharkiv.dereza.bookmaker.dao.TrialStatusDAO;

/**
 * DAO factory for mysql.
 * 
 * @author Eduard
 *
 */
public class MysqlDAOFactory extends DAOFactory {

	private static final Logger log = Logger.getLogger(MysqlDAOFactory.class);

	private static Context initContext;
	private static Context envContext;
	private static DataSource ds;

	static {
		try {
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");

			// BM - the name of data source
			ds = (DataSource) (envContext.lookup("jdbc/BM"));
		} catch (NamingException ex) {
			log.error("Cannot init pool of connection", ex);
		}
	}

	/**
	 * Creates connection to MySql db.
	 * 
	 * @return Connection
	 */
	public static Connection createConnection() {
		Connection connection = null;
		try {
			connection = ds.getConnection();
		} catch (SQLException ex) {
			log.error("Cannot get connection from Datasourse", ex);
		}
		return connection;
	}

	/**
	 * Commits query and closes connection.
	 * 
	 * @param ResultSet
	 * @param Statement
	 * @param Connection
	 */
	public static void commitAndClose(ResultSet rs, Statement ps,
			Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Cannot close result set", e);
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				log.error("Cannot close statement", e);
			}
		}
		if (conn != null) {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				log.error("Cannot commit and close connection", e);
			}
		}
	}

	/**
	 * In case of fail does rollback.
	 * 
	 * @param Connection
	 */
	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				log.error("Cannot rollback", e);
			}
		}
	}

	@Override
	public BetDAO getBetDAO() {
		return new MysqlBetDAO();
	}

	@Override
	public ClientDAO getClientDAO() {
		return new MysqlClientDAO();
	}

	@Override
	public ClientStatusDAO getClientStatusDAO() {
		return new MysqlClientStatusDAO();
	}

	@Override
	public CoverDAO getCoverDAO() {
		return new MysqlCoverDAO();
	}

	@Override
	public DependenceAgeCoverDAO getDependenceAgeCoverDAO() {
		return new MysqlDependenceAgeCoverDAO();
	}

	@Override
	public DependenceAgeDistanceDAO getDependenceAgeDistanceDAO() {
		return new MysqlDependenceAgeDistanceDAO();
	}

	@Override
	public DependenceAgeTrackTypeDAO getDependenceAgeTrackTypeDAO() {
		return new MysqlDependenceAgeTrackTypeDAO();
	}

	@Override
	public DistanceDAO getDistanceDAO() {
		return new MysqlDistanceDAO();
	}

	@Override
	public HorseDAO getHorseDAO() {
		return new MysqlHorseDAO();
	}

	@Override
	public HorseStatusDAO getHorseStatusDAO() {
		return new MysqlHorseStatusDAO();
	}

	@Override
	public LocaleDAO getLocaleDAO() {
		return new MysqlLocaleDAO();
	}

	@Override
	public OwnerDAO getOwnerDAO() {
		return new MysqlOwnerDAO();
	}

	@Override
	public RoleDAO getRoleDAO() {
		return new MysqlRoleDAO();
	}

	@Override
	public TrackDAO getTrackDAO() {
		return new MysqlTrackDAO();
	}

	@Override
	public TrackTypeDAO getTrackTypeDAO() {
		return new MysqlTrackTypeDAO();
	}

	@Override
	public TrialDAO getTrialDAO() {
		return new MysqlTrialDAO();
	}

	@Override
	public TrialHorseDAO getTrialHorseDAO() {
		return new MysqlTrialHorseDAO();
	}

	@Override
	public TrialStatusDAO getTrialStatusDAO() {
		return new MysqlTrialStatusDAO();
	}
}