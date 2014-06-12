package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.OwnerDAO;
import ua.kharkiv.dereza.bookmaker.dto.OwnerDTO;

/**
 * Implementation OwnerDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlOwnerDAO implements OwnerDAO{

	private static final Logger log = Logger.getLogger(MysqlLocaleDAO.class);

	private Connection conn;
	
	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_OWNER_BY_ID = "SELECT * FROM owners WHERE id=?;";
	
	@Override
	public OwnerDTO findOwnerById(int id) {
		OwnerDTO ownerDTO = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_OWNER_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			ownerDTO = new OwnerDTO();
			if (rs.next()) {
				ownerDTO.setId(rs.getInt(1));
				ownerDTO.setName(rs.getString(2));
				ownerDTO.setSurname(rs.getString(3));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return ownerDTO;
	}
}