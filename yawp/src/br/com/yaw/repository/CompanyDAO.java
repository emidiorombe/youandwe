package br.com.yaw.repository;

import java.util.ArrayList;
import java.util.Collection;
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
			if(c != null)c.getAddr();
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

	@Override
	public void removeCompany(long companyId) throws RepositoryException {
		try {
			beginTransaction();
			Company c = getByPrimaryKey(KeyFactory.createKey("Company", companyId));
			delete(c);
			commitTransaction();
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		
	}

	@Override
	public Collection<Company> getByName(String query) throws RepositoryException {
		List<Company> list = new ArrayList<Company>();
		try{
			StringBuilder jql = new StringBuilder();
			jql.append("select ct from Company ct where ct.name = :comp_name");
			addParamToQuery("comp_name", query);
			list = executeQuery(jql.toString(), paramsToQuery);
			list.size();
		}catch (RepositoryException re) {
			throw re;
		}finally {
		}
		return list;
	}

}
