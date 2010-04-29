package br.com.yaw.repository;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import br.com.yaw.entity.Company;
import br.com.yaw.exception.RepositoryException;

public class CompanyDAO extends BaseDAO<Company, Key> implements CompanyRepository{

	@Override
	public Company getById(long id) throws RepositoryException {
		return getByPrimaryKey(KeyFactory.createKey("Company", id));
	}

	@Override
	public void addCompany(Company company) throws RepositoryException {
		save(company);
	}

	@Override
	public List<Company> getAllCompanies() throws RepositoryException {
		return getAll();
	}
	
	

}
