package br.com.globalcode.dao;

import br.com.globalcode.model.Movimento;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rafael Nunes
 */
@Stateless
public class MovimentoDAOImpl implements MovimentoDAO{
    
    @PersistenceContext(name="provaCDI")
    private EntityManager em;
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void salvar(Movimento movimento) {
        em.persist(movimento);
    }

    @Override
    public List<Movimento> buscarTodos() {
        return em.createQuery("select m from Movimento m").getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void excluir(Integer idMovimento) {
        Movimento mdel = em.find(Movimento.class, idMovimento);
        em.remove(mdel);
    }
    
}
