package br.com.newti.dao;

import java.sql.*;

import org.apache.log4j.Logger;

import br.com.newti.exception.DAOException;

public class ConnectionManager {
	//TODO buscar parametros no web.xml
	private static final String STR_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String STR_CON = "jdbc:oracle:thin:@//127.0.0.1:1521/XE";
	private static final String USER = "system";
	private static final String PASSWORD = "oracle";
	private static final Logger log = Logger.getLogger(ConnectionManager.class);

	public static Connection getConexao() throws DAOException {
		//TODO Implementar Pool de Conexões
		
		Connection conn = null;
		try {
			Class.forName(STR_DRIVER);
			conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
			return conn;
		} catch (ClassNotFoundException e) {
			String errorMsg = "Driver nao encontrado";
			throw new DAOException(errorMsg, e);
		} catch (SQLException e) {
			String errorMsg = "Erro ao obter a conexao";
			throw new DAOException(errorMsg, e);
		}
	}

	public static void closeAll(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			log.error("Nao foi possivel fechar a conexaoao com o banco");
		}
		ConnectionManager.closeAll(conn);
	}

	public static void closeAll(Connection conn, Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			log.error("Nao foi possivel fechar o statement");
		}
		ConnectionManager.closeAll(conn, stmt);
	}

	public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}

		} catch (Exception e) {
			log.error("Nao foi possivel fechar o ResultSet no banco");
		}
		ConnectionManager.closeAll(conn, stmt, rs);
	}
	
}
