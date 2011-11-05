package br.com.globalcode.transporte;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@EJB
public class ComunicacaoEJB implements Comunicacao{

    @Override
    public void comunicarComBanco(String dados) {
        System.out.println("Comunicando com o banco via EJB");
    }
    
}
