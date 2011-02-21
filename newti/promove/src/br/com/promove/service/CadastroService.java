package br.com.promove.service;

import java.util.Date;
import java.util.List;

import br.com.promove.entity.Clima;
import br.com.promove.entity.Cor;
import br.com.promove.entity.Fabricante;
import br.com.promove.entity.Filial;
import br.com.promove.entity.Modelo;
import br.com.promove.entity.TipoUsuario;
import br.com.promove.entity.Usuario;
import br.com.promove.entity.Veiculo;
import br.com.promove.exception.PromoveException;

/**
 * Serviços de cadastros gerais
 * @author Rafael Nunes
 *
 */
public interface CadastroService {
	public void salvarCor(Cor cor) throws PromoveException;

	public List<Cor> buscarTodasCores() throws PromoveException;

	public void excluirCor(Cor bean)throws PromoveException;

	public void salvarModelo(Modelo bean)throws PromoveException;

	public void excluirModelo(Modelo bean)throws PromoveException;
	
	public List<Modelo> buscarTodosModelos()throws PromoveException;

	public List<Fabricante> buscarTodosFabricantes()throws PromoveException;

	public void salvarFabricante(Fabricante bean) throws PromoveException;

	public void excluirFabricante(Fabricante bean) throws PromoveException;

	public void salvarFilial(Filial bean)throws PromoveException;

	public void excluirFilial(Filial bean)throws PromoveException;

	public List<Filial> buscarTodasFiliais()throws PromoveException;

	public void salvarUsuario(Usuario bean)throws PromoveException;
	
	public void excluirUsuario(Usuario bean)throws PromoveException;
	
	public List<Usuario> buscarTodosUsuarios()throws PromoveException;

	public List<TipoUsuario> buscarTodosTiposUsuarios()throws PromoveException;

	public void salvarVeiculo(Veiculo bean)throws PromoveException;

	public void excluirVeiculo(Veiculo bean)throws PromoveException;

	public List<Veiculo> buscarTodosVeiculos()throws PromoveException;

	public List<Veiculo> buscarVeiculoPorFiltro(String chassi, Date dtInicio, Date dtFim)throws PromoveException;

}
