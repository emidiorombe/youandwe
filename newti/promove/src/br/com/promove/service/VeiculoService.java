package br.com.promove.service;

import java.util.List;

import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;

public interface VeiculoService {

	List<Veiculo> buscarTodosVeiculos() throws PromoveException;

}
