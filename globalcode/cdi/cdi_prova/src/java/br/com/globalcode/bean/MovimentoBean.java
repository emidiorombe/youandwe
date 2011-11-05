package br.com.globalcode.bean;

import br.com.globalcode.model.Movimento;
import br.com.globalcode.service.MovimentoService;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes
 */
@Named
@RequestScoped
public class MovimentoBean {
    @Inject
    private MovimentoService mService;
    
    private List<Movimento> movimentos;
    private Movimento movimento;
    private int movimentoDel;
    
    
    public MovimentoBean(){
    }
    
    
    public void salvar(){
        mService.salvar(movimento);
    }
    
    public void excluir(){
        mService.excluir(movimentoDel);
    }
    

    public List<Movimento> getMovimentos() {
        movimentos = mService.buscarTodosMovimentos();
        
        return movimentos;
    }

    public void setMovimentos(List<Movimento> movimentos) {
        this.movimentos = movimentos;
    }

    public Movimento getMovimento() {
        if(movimento == null)
            movimento = new Movimento();
        return movimento;
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }

    public int getMovimentoDel() {
        return movimentoDel;
    }

    public void setMovimentoDel(int movimentoDel) {
        this.movimentoDel = movimentoDel;
    }
    
}
