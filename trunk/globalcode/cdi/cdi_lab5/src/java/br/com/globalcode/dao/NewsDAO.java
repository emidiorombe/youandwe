package br.com.globalcode.dao;

import java.util.List;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
public interface NewsDAO {
    public void salvar(String mail);
    
    public List<String> getMails();
    
}
