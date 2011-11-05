package br.com.globalcode.bean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@Named
@RequestScoped
public class RequestBean implements Serializable {
    private Integer contador = 0;
    
    public void incrementar(){
        ++contador;
    }
    
    public Integer getContador(){
        return contador;
    }
}
