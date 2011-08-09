package br.com.newti.dao;

import java.beans.PropertyVetoException;
import java.sql.*;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import br.com.newti.exception.DAOException;

public class ConnectionManager {
	private static final Logger log = Logger.getLogger(ConnectionManager.class);
	private static DataSource pooled;

	public static Connection getConexao() throws DAOException {
		try {
			return pooled.getConnection();
		} catch (SQLException e) {
			throw new DAOException();
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
	
	public static void initialize(String driver, String url, String user, String pass ) throws DAOException {
		try {
			Class.forName(driver);
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass(driver);          
			cpds.setJdbcUrl(url);
			cpds.setUser(user);                                  
			cpds.setPassword(pass);      
			
			//configs
			cpds.setMinPoolSize(5);                                     
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(20);
			
			pooled = DataSources.pooledDataSource(cpds);
		} catch (ClassNotFoundException e) {
			String errorMsg = "Driver nao encontrado";
			throw new DAOException(errorMsg, e);
		} catch (SQLException e) {
			String errorMsg = "Erro ao obter a conexao";
			throw new DAOException(errorMsg, e);
		} catch (PropertyVetoException e) {
			String errorMsg = "Erro ao obter a conexao";
			throw new DAOException(errorMsg, e);
		}
	}
	
}
