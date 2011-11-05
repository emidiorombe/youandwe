package br.com.globalcode.caixa;

import br.com.globalcode.transporte.Comunicacao;
import br.com.globalcode.transporte.EJB;
import br.com.globalcode.transporte.Socket;
import br.com.globalcode.transporte.WebService;
import java.math.BigDecimal;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@Named("caixaQ")
public class CaixaEletronicoImpl implements CaixaEletronico{
    
    @Inject
    @WebService
    private Comunicacao transporte;
    
    
    @Override
    public void depositar(BigDecimal bd) {
        transporte.comunicarComBanco(bd.toString());
        System.out.println("Realizando deposito de R$" + bd.doubleValue());
    }

    @Override
    public void sacar(BigDecimal bd) {
        transporte.comunicarComBanco(bd.toString());
        System.out.println("Realizando saque de R$" + bd.doubleValue());
    }
    
}
