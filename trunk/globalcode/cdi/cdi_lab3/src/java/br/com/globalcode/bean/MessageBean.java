package br.com.globalcode.bean;

import java.io.Serializable;
import java.util.Random;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes
 */
@Named
public class MessageBean implements Serializable{
    
    public String getMessage(){
        return "ManagedBean injetado via CDI ";
    }
    
    public void click(){
        System.out.println("Executando método do ManagedBean");
    }

    public Integer getNumber() {
        return new Random().nextInt();
    }

    
    
}
