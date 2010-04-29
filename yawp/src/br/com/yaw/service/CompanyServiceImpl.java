package br.com.yaw.service;

import java.util.ArrayList;
import java.util.List;

import br.com.yaw.entity.Company;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.repository.CompanyRepository;

public class CompanyServiceImpl implements CompanyService{
	private CompanyRepository companyRepository;
	
	
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
}
