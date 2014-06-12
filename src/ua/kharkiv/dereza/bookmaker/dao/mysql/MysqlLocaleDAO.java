package ua.kharkiv.dereza.bookmaker.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.kharkiv.dereza.bookmaker.dao.LocaleDAO;
import ua.kharkiv.dereza.bookmaker.dto.LocaleDTO;

/**
 * Implementation LocaleDAO for mysql db.
 * 
 * @author Eduard
 *
 */
public class MysqlLocaleDAO implements LocaleDAO{
	
	private static final Logger log = Logger.getLogger(MysqlLocaleDAO.class);

	private Connection conn;

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_LOCALE_BY_ID = "SELECT * FROM locales WHERE id=?;";
	private static final String SQL_FIND_LOCALE_NAME_BY_NAME = "SELECT * FROM locales WHERE name=?;";
	private static final String SQL_FIND_ALL_LOCALES = "SELECT name FROM locales;";
	
	@Override
	public LocaleDTO findLocaleById(int id) {
		LocaleDTO localeDTO = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_LOCALE_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			localeDTO = new LocaleDTO();
			if (rs.next()) {
				localeDTO.setId(rs.getInt(1));
				localeDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return localeDTO;
	}

	@Override
	public List<LocaleDTO> findAllLocales() {
		LocaleDTO localeDTO = null;
		List<LocaleDTO> list = null;

		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_LOCALES);
			list = new LinkedList<LocaleDTO>();
			localeDTO = new LocaleDTO();
			while (rs.next()) {
				localeDTO.setName(rs.getString(1));
				list.add(localeDTO);
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, stmt, conn);
		}
		return list;
	}

	@Override
	public LocaleDTO findLocaleByName(String name) {
		LocaleDTO localeDTO = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = MysqlDAOFactory.createConnection();
			pstmt = conn.prepareStatement(SQL_FIND_LOCALE_NAME_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			localeDTO = new LocaleDTO();
			if (rs.next()) {
				localeDTO.setId(rs.getInt(1));
				localeDTO.setName(rs.getString(2));
			}
		} catch (SQLException ex) {
			log.error("Cannot do query", ex);
			MysqlDAOFactory.rollback(conn);
		} finally {
			MysqlDAOFactory.commitAndClose(rs, pstmt, conn);
		}
		return localeDTO;
	}
}