package importacao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.promove.entity.OrigemAvaria;


public class ImportOrigemAvaria {
	private static SessionFactory sf;
	private static Session session;
	
	public static void main(String[] args) {
		loadHbm();
		try {
			for (String linha : FileUtils.loadFile("origemavaria.txt")) {
				criarClima(linha);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			session.getTransaction().rollback();
		}
		
		commit();
	}

	private static void commit() {
		session.getTransaction().commit();
		
	}

	private static void criarClima(String linha) {
		String[] campos = linha.split(";");
		
		OrigemAvaria origem = new OrigemAvaria();
		origem.setId(new Integer(campos[0]));
		origem.setCodigo(new Integer(campos[1]));
		origem.setDescricao(campos[2]);
		origem.setResponsabilidade(campos[3]);
		origem.setSigla(campos[4]);
		
		
		session.save(origem);
		
	}

	private static void loadHbm() {
		AnnotationConfiguration conf = new AnnotationConfiguration();
		sf = conf.configure().buildSessionFactory();
		session = sf.openSession();
		session.beginTransaction();
	}
}	
