package importacao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.promove.entity.Filial;


public class ImportFilial {
	private static SessionFactory sf;
	private static Session session;
	
	public static void main(String[] args) {
		loadHbm();
		try {
			for (String linha : FileUtils.loadFile("filial.txt")) {
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
		
		Filial filial = new Filial();
		filial.setId(new Integer(campos[0]));
		filial.setCodigo(new Integer(campos[1]));
		filial.setCodigoExterno(new Integer(campos[2]));
		filial.setNome(campos[3]);
		filial.setSigla(campos[4]);
		
		session.save(filial);
		
	}

	private static void loadHbm() {
		AnnotationConfiguration conf = new AnnotationConfiguration();
		sf = conf.configure().buildSessionFactory();
		session = sf.openSession();
		session.beginTransaction();
	}
}
