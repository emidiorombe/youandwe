package br.com.newti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.newti.exception.DAOException;
import br.com.newti.util.SQLCache;

public class CTRCDAO {

	public static List<Map<String, Object>> getByDataModificacao(Date dataInicial, Date dataFinal) {
		List<Map<String, Object>> retorno = new ArrayList<Map<String, Object>>();
		try {
			Connection con = ConnectionManager.getConexao();
			PreparedStatement pstmt = con.prepareStatement(SQLCache.getScript("ctrc"));
			pstmt.setDate(1, new java.sql.Date(dataInicial.getTime()));
			pstmt.setDate(2, new java.sql.Date(dataFinal.getTime()));
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> ctrc = new HashMap<String, Object>();
				int size = rs.getMetaData().getColumnCount();
				for(int i = 1; i < size; i++) {
					ctrc.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
				}
				retorno.add(ctrc);
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}
}
