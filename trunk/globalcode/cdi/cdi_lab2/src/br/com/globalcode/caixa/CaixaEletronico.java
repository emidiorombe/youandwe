package br.com.globalcode.caixa;

import br.com.globalcode.transporte.Comunicacao;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Nunes
 */
public interface CaixaEletronico {
    void depositar(BigDecimal bd);

    void sacar(BigDecimal bd);
    
}
