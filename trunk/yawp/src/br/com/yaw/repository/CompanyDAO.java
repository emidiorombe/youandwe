package br.com.yaw.repository;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import br.com.yaw.entity.Company;
import br.com.yaw.exception.RepositoryException;

public class CompanyDAO extends BaseDAO<Company, Key> implements CompanyRepository{

	@Override
	public Company getById(long id) throws RepositoryException {
		Company c = null;
		try{
			beginTransaction();
			c = getByPrimaryKey(KeyFactory.createKey("Company", id));
			c.getAddr();
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		return c;
	}

	@Override
	public void addCompany(Company company) throws RepositoryException {
		try {
			beginTransaction();
			save(company);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
	}

	@Override
	public List<Company> getAllCompanies() throws RepositoryException {
		List<Company> list = new ArrayList<Company>();
		try{
			beginTransaction();
			list = getAll();
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		return list;
	}
	
	

}
