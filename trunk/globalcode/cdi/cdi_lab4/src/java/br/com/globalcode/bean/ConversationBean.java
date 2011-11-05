package br.com.globalcode.bean;

import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@Named
@ConversationScoped
public class ConversationBean implements Serializable{
    private Integer contador = 0;
    
    @Inject
    private Conversation conversation;
    
    public void incrementar(){
      if (conversation.isTransient())
      {
          conversation.begin();
      }
        ++contador;
    }
    
    public void finalizar(){
      if (!conversation.isTransient())
      {
          conversation.end();
      }
    }
    
    public Integer getContador(){
        return contador;
    }
}
