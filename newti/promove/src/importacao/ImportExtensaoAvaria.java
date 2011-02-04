package importacao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.promove.entity.ExtensaoAvaria;


public class ImportExtensaoAvaria {
	private static SessionFactory sf;
	private static Session session;
	
	public static void main(String[] args) {
		loadHbm();
		try {
			for (String linha : FileUtils.loadFile("extensaoavaria.txt")) {
				criarClima(linha);
			}
		}catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		commit();
	}

	private static void commit() {
		session.getTransaction().commit();
		
	}

	private static void criarClima(String linha) {
		String[] campos = linha.split(";");
		
		ExtensaoAvaria ext = new ExtensaoAvaria();
		ext.setId(new Integer(campos[0]));
		ext.setCodigo(new Integer(campos[1]));
		ext.setDescricao(campos[2]);
		
		session.save(ext);
		
	}

	private static void loadHbm() {
		AnnotationConfiguration conf = new AnnotationConfiguration();
		sf = conf.configure().buildSessionFactory();
		session = sf.openSession();
		session.beginTransaction();
	}
}
