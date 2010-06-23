package br.com.yaw.service;

import java.util.ArrayList;
import java.util.List;

import br.com.yaw.entity.Company;
import br.com.yaw.entity.CompanyTag;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.repository.CompanyRepository;
import br.com.yaw.repository.CompanyTagRepository;

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
				if(!tag.equals(","))
					lista.add(new CompanyTag(tag.trim(), companyId));
			}
			tagRepository.addTagsToCompany(lista);
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
	public void updateCompany(Company company) throws ServiceException {
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			companyRepository.addCompany(null);
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.inserir.empresa", re);
		}
		
	}
}
