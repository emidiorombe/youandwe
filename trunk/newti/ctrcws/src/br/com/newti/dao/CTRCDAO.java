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

	public static Map<Integer, Map<String, Object>> getByDataModificacao(Date dataInicial, Date dataFinal) {
		Map<Integer, Map<String, Object>> retorno = new HashMap<Integer, Map<String, Object>>();
		try {
			Connection con = ConnectionManager.getConexao();
			PreparedStatement pstmt = con.prepareStatement(SQLCache.getScript("ctrc"));
			pstmt.setDate(1, new java.sql.Date(dataInicial.getTime()));
			pstmt.setDate(2, new java.sql.Date(dataFinal.getTime()));
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int size = rs.getMetaData().getColumnCount();
				Integer id = new Integer(rs.getObject(1).toString());
				Map<String, Object> veic = new HashMap<String, Object>();
				
				Map<String, Object> ctrc = retorno.get(id);
				if (ctrc == null) {
					ctrc = new HashMap<String, Object>();
				}
				
				for(int i = 1; i <= size; i++) {
					String chave = rs.getMetaData().getColumnName(i).toLowerCase();
					
					if (chave.startsWith("veiculo")) {
						veic.put(chave, rs.getObject(i));						
					} else if (!ctrc.containsKey(chave)) {
						ctrc.put(chave, rs.getObject(i));						
					}
				}

				List<Map<String, Object>> veics = (List<Map<String, Object>>)ctrc.get("veiculo");
				if (veics == null) {
					veics = new ArrayList<Map<String, Object>>();
				}
				
				veics.add(veic);
				ctrc.put("veiculo", veics);
				
				retorno.put(id, ctrc);
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
