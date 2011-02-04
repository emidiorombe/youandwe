package importacao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import br.com.promove.entity.Fabricante;
import br.com.promove.entity.Modelo;


public class ImportModelo {
	private static SessionFactory sf;
	private static Session session;
	
	public static void main(String[] args) {
		loadHbm();
		try {
			for (String linha : FileUtils.loadFile("modelo.txt")) {
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
		
		Modelo modelo = new Modelo();
		modelo.setId(new Integer(campos[0]));
		modelo.setCodigo(campos[1]);
		modelo.setDescricao(campos[2]);
		
		Fabricante f = new Fabricante();
		f.setId(new Integer(campos[3]));

		modelo.setFabricante(f);
		
		session.save(modelo);
		
	}

	private static void loadHbm() {
		AnnotationConfiguration conf = new AnnotationConfiguration();
		sf = conf.configure().buildSessionFactory();
		session = sf.openSession();
		session.beginTransaction();
	}
}
