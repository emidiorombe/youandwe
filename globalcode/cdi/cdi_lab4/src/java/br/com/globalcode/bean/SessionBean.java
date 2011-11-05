package br.com.globalcode.bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@Named
@SessionScoped
public class SessionBean implements Serializable {
    private Integer contador = 0;
    
    public void incrementar(){
        ++contador;
    }
    
    public Integer getContador(){
        return contador;
    }
}
