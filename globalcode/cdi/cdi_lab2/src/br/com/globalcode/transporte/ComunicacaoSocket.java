package br.com.globalcode.transporte;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@Socket
public class ComunicacaoSocket implements Comunicacao{

    @Override
    public void comunicarComBanco(String dados) {
        System.out.println("Comunicando com o banco via Socket na porta 1234");
    }
    
}
