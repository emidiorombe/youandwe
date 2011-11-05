package br.com.globalcode.bean;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@Named
@ApplicationScoped
public class ApplicationBean implements Serializable{
    private Integer contador = 0;
    
    public void incrementar(){
        ++contador;
    }
    
    public Integer getContador(){
        return contador;
    }
}
