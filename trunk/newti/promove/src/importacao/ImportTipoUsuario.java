package importacao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.promove.entity.TipoUsuario;


public class ImportTipoUsuario {
	private static SessionFactory sf;
	private static Session session;
	
	public static void main(String[] args) {
		loadHbm();
		try {
			for (String linha : FileUtils.loadFile("tipousuario.txt")) {
				criarClima(linha);
			}
		}catch(Exception e) {
			session.getTransaction().rollback();
		}
		
		commit();
	}

	private static void commit() {
		session.getTransaction().commit();
		
	}

	private static void criarClima(String linha) {
		String[] campos = linha.split(";");
		
		TipoUsuario tipo = new TipoUsuario();
		tipo.setId(new Integer(campos[0]));
		tipo.setNome(campos[1]);
		
		session.save(tipo);
		
	}

	private static void loadHbm() {
		AnnotationConfiguration conf = new AnnotationConfiguration();
		sf = conf.configure().buildSessionFactory();
		session = sf.openSession();
		session.beginTransaction();
	}
}
