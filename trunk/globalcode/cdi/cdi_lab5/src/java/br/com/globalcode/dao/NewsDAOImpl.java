package br.com.globalcode.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Rafael Nunes <rafael@yaw.com.br>
 */
@Named
public class NewsDAOImpl implements NewsDAO, Serializable{
    
    private static List<String> mails;

    @Override
    public void salvar(String mail) {
        mails.add(mail);
    }

    @Override
    public List<String> getMails() {
        if(mails == null)
            mails = new ArrayList<String>();
        
        return mails;
    }
    
}
