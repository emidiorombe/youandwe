package br.com.globalcode.service;

import br.com.globalcode.dao.MovimentoDAO;
import br.com.globalcode.model.Movimento;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Rafael Nunes
 */
public class MovimentoService {
   
    @Inject
    private MovimentoDAO movimentoDAO;
    
    public void salvar(Movimento movimento){
        movimentoDAO.salvar(movimento);
    }
    
    public List<Movimento> buscarTodosMovimentos(){
        return movimentoDAO.buscarTodos();
    }
    
    public void excluir(Integer idMovimento){
        movimentoDAO.excluir(idMovimento);
    }
}
