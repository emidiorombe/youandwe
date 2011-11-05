package br.com.globalcode.bean;

import br.com.globalcode.service.NewsService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@Named
@SessionScoped
public class NewsBean implements Serializable{
    private String mail;
    
    @Inject
    private NewsService newsService;
    
    public void salvar(){
        newsService.salvar(mail);
        mail = null;
    }
    
    public List<String> getAssinantes(){
        return newsService.buscarAssinantes();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    
}
