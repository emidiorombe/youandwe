package importacao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.promove.entity.LocalAvaria;


public class ImportLocalAvaria {
	private static SessionFactory sf;
	private static Session session;
	
	public static void main(String[] args) {
		loadHbm();
		try {
			for (String linha : FileUtils.loadFile("localavaria.txt")) {
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
		
		LocalAvaria local = new LocalAvaria();
		local.setId(new Integer(campos[0]));
		local.setCodigo(new Integer(campos[2]));
		local.setAcessorio(FileUtils.getBoolean(campos[1]));
		local.setDescricao(campos[3]);
		
		session.save(local);
		
	}

	private static void loadHbm() {
		AnnotationConfiguration conf = new AnnotationConfiguration();
		sf = conf.configure().buildSessionFactory();
		session = sf.openSession();
		session.beginTransaction();
	}
}	
