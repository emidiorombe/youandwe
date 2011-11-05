package br.com.globalcode.transporte;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@WebService
public class ComunicacaoWebService implements Comunicacao{

    @Override
    public void comunicarComBanco(String dados) {
        System.out.println("Comunicando com o banco via WebService SOAP");
    }
    
}
