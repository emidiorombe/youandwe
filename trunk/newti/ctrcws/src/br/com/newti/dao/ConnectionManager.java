package br.com.newti.dao;

import java.sql.*;

import org.apache.log4j.Logger;

import br.com.newti.exception.DAOException;

public class ConnectionManager {

	private static final String STR_DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DATABASE = "clientes";
	private static final String IP = "localhost";
	private static final String STR_CON = "jdbc:mysql://" + IP + ":3306/"
			+ DATABASE;
	private static final String USER = "root";
	private static final String PASSWORD = "mysql";
	private static final Logger log = Logger.getLogger(ConnectionManager.class);

	public static Connection getConexao() throws DAOException {
		Connection conn = null;
		try {
			Class.forName(STR_DRIVER);
			conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
			System.out.println("[ConnectionManager]: Obtendo conexao");
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
