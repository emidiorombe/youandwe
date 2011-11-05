package br.com.globalcode.teste;

import br.com.globalcode.caixa.CaixaEletronico;
import java.math.BigDecimal;
import org.cdisource.beancontainer.BeanContainer;
import org.cdisource.beancontainer.BeanContainerManager;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
public class CaixaEletronicoTeste {
    static BeanContainer beanContainer =  BeanContainerManager.getInstance();

    public static void main(String args[]){
        CaixaEletronico cxq = (CaixaEletronico) beanContainer.getBeanByName("caixaQ");
        
        cxq.depositar(new BigDecimal(2500.0));
        cxq.sacar(new BigDecimal(500.0));
        
    }
}
