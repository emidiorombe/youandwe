package br.com.promove.service;

import java.util.List;

import br.com.promove.dao.VeiculoDAO;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.DAOException;
import br.com.promove.exception.PromoveException;

public class VeiculoServiceImpl implements VeiculoService{
	private VeiculoDAO veiculoDAO;
	
	public VeiculoServiceImpl() {
		veiculoDAO = new VeiculoDAO();
	}
	
	@Override
	public List<Veiculo> buscarTodosVeiculos() throws PromoveException {
		List<Veiculo> lista = null;
		try {
			lista = veiculoDAO.getAll();
		} catch (DAOException de) {
			throw new PromoveException(de);
		}
		return lista;
	}

}
