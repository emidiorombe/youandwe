package br.com.yaw.service;

import java.util.ArrayList;
import java.util.List;

import org.compass.core.CompassHits;
import org.compass.core.CompassSearchSession;
import org.compass.core.Resource;

import br.com.yaw.async.AsyncJobs;
import br.com.yaw.entity.Company;
import br.com.yaw.entity.CompanyTag;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.repository.CompanyRepository;
import br.com.yaw.repository.CompanyTagRepository;
import br.com.yaw.repository.CompassFactory;

public class CompanyServiceImpl implements CompanyService{
	private CompanyRepository companyRepository;
	private CompanyTagRepository tagRepository;
	
	
	@Override
	public Company getCompanyById(long id) throws ServiceException {
		Company c = null;
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			c = companyRepository.getById(id);
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.empresa", re);
		}
		return c;
	}
	
	public void addCompany(Company c) throws ServiceException{
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			companyRepository.addCompany(c);
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.inserir.empresa", re);
		}
	}

	@Override
	public List<Company> getAllCompanies() throws ServiceException {
		List<Company> lista = new ArrayList<Company>();
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			lista = companyRepository.getAllCompanies();
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.empresa", re);
		}
		return lista;
	}

	@Override
	public void addTags(String parameter, Long companyId) throws ServiceException {
		String tokens[] = parameter != null ?  parameter.split(",") : new String[0];
		try {
			List<CompanyTag> lista = new ArrayList<CompanyTag>();
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			tagRepository = ServiceFactory.getService(CompanyTagRepository.class);
			tagRepository.deleteCompanyTags(companyId);
			
			for (String tag : tokens) {
				if(!tag.equals(",") && !tag.equals(" ") && !tag.equals(""))
					lista.add(new CompanyTag(tag.trim(), companyId));
			}
			tagRepository.addTagsToCompany(lista);
			AsyncJobs.rebuildCompassIndex();
		}catch (RepositoryException re) {
			re.printStackTrace();
			throw new ServiceException(re);
		}
		
	}

	@Override
	public List<CompanyTag> getCompanyTags(long companyId) throws ServiceException {
		List<CompanyTag> lista = new ArrayList<CompanyTag>();
		try {
			tagRepository = ServiceFactory.getService(CompanyTagRepository.class);
			lista = tagRepository.getTagsByCompany(companyId);
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.empresa", re);
		}
		return lista;
	}

	@Override
	public void remove(long companyId) throws ServiceException {
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			companyRepository.removeCompany(companyId);
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.remover.empresa", re);
		}
		
	}

	@Override
	public List<Company> findCompanies(String query) throws ServiceException {
		List<Company> lista = new ArrayList<Company>();
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			CompassSearchSession search = CompassFactory.getCompass().openSearchSession();
		 	
		 	if(query != null){
		 		CompassHits hits = search.find(query);
		 		for(int i = 0; i < hits.length(); i++){
		 	 		Resource resource = hits.resource(i);
		 	 		String id = resource.getValue("companyId");
		 	 		Company c = companyRepository.getById(Long.parseLong(id));
		 	 		if(c != null)lista.add(c);
		 		}
		 	}
		 	search.close();
		}catch(RepositoryException re) {
			throw new ServiceException(re);
		}
		return lista;
	}
}
