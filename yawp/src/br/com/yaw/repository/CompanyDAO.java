package br.com.yaw.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import br.com.yaw.entity.Company;
import br.com.yaw.entity.CompanyTag;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.utils.StringUtilities;

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
			jql.append("select ct from Company ct where ct.searchableName >=:comp_name and  ct.searchableName < :delimit");
			addParamToQuery("comp_name", query);
			addParamToQuery("delimit", query + "\ufffd");
			list = executeQuery(jql.toString(), paramsToQuery);
			list.size();
		}catch (RepositoryException re) {
			throw re;
		}finally {
		}
		return list;
	}

	@Override
	public Collection<Company> getByCity(String city)throws RepositoryException {
		List<Company> list = new ArrayList<Company>();
		try{
			StringBuilder jql = new StringBuilder();
			jql.append("select ct from Company ct where ct.addr.searchableCity >= :city_name and ct.addr.searchableCity < :delimit");
			addParamToQuery("city_name", city);
			addParamToQuery("delimit", city + "\ufffd");
			list = executeQuery(jql.toString(), paramsToQuery);
			list.size();
		}catch (RepositoryException re) {
			throw re;
		}finally {
		}
		return list;
	}

	@Override
	public Collection<Company> getByTag(String tag)
			throws RepositoryException {
		List<Company> list = new ArrayList<Company>();
		try{
			StringBuilder jql = new StringBuilder();
			jql.append("select ct from CompanyTag ct where ct.searchableName >=:comp_name and  ct.searchableName < :delimit");
			addParamToQuery("comp_name", tag);
			addParamToQuery("delimit", tag + "\ufffd");
			List<CompanyTag> tags = executeQuery(jql.toString(), paramsToQuery);
			
			if(tags != null && tags.size() > 0) {
				List<Long> keys = new ArrayList<Long>();
				for (CompanyTag ctag : tags) {
					keys.add(ctag.getCompanyId());
				}
				StringBuilder jql2 = new StringBuilder();
				jql2.append("select ct from Company ct where ct.key in " + StringUtilities.listLongToInClause(keys));
				list = executeQuery(jql2.toString(), paramsToQuery);
				list.size();
			}
			
		}catch (RepositoryException re) {
			throw re;
		}finally {
		}
		return list;
	}

	@Override
	public Collection<Company> getByBairro(String bairro) throws RepositoryException {
		List<Company> list = new ArrayList<Company>();
		try{
			StringBuilder jql = new StringBuilder();
			jql.append("select ct from Company ct where ct.addr.searchableBairro >= :bairro_name and ct.addr.searchableBairro < :delimit");
			addParamToQuery("bairro_name", bairro);
			addParamToQuery("delimit", bairro + "\ufffd");
			list = executeQuery(jql.toString(), paramsToQuery);
			list.size();
		}catch (RepositoryException re) {
			throw re;
		}finally {
		}
		return list;
	}

}
