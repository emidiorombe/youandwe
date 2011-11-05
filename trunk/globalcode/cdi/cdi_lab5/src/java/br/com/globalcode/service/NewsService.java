package br.com.globalcode.service;

import br.com.globalcode.dao.NewsDAO;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@Named
public class NewsService implements Serializable {
    @Inject
    private NewsDAO newsDAO;
    
    public void salvar(String mail){
        newsDAO.salvar(mail);
    }
    
    public List<String> buscarAssinantes(){
        return newsDAO.getMails();
    }
}
