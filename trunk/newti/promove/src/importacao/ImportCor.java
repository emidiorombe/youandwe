package importacao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.promove.entity.Cor;


public class ImportCor {
	private static SessionFactory sf;
	private static Session session;
	
	public static void main(String[] args) {
		loadHbm();
		try {
			for (String linha : FileUtils.loadFile("cor.txt")) {
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
		
		Cor cor = new Cor();
		cor.setId(new Integer(campos[0]));
		cor.setCodigo(new Integer(campos[1]));
		cor.setDescricao(campos[2]);
		
		session.save(cor);
		
	}

	private static void loadHbm() {
		AnnotationConfiguration conf = new AnnotationConfiguration();
		sf = conf.configure().buildSessionFactory();
		session = sf.openSession();
		session.beginTransaction();
	}
}
