package br.com.globalcode.dao;

import br.com.globalcode.model.Movimento;
import java.util.List;

/**
 *
 * @author Rafael Nunes
 */
public interface MovimentoDAO {

    public void salvar(Movimento movimento);
    
    public List<Movimento> buscarTodos();
    
    public void excluir(Integer idMovimento);
    
}
