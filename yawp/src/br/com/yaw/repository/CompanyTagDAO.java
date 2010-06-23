package br.com.yaw.repository;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Key;

import br.com.yaw.entity.CompanyTag;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.utils.StringUtilities;

public class CompanyTagDAO extends BaseDAO<CompanyTag, Key> implements CompanyTagRepository{
	
	@Override
	public void addTagsToCompany(List<CompanyTag> lista)
			throws RepositoryException {
		try {
			for (CompanyTag companyTag : lista) {
				beginTransaction();
				save(companyTag);
				commitTransaction();
			}
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		
	}

	@Override
	public List<CompanyTag> getTagsByCompany(Long companyId) throws RepositoryException {
		List<CompanyTag> li = new ArrayList<CompanyTag>();
		try {
			
			StringBuilder jql = new StringBuilder();
			jql.append("select ct from CompanyTag ct where ct.companyId = :cid");
			addParamToQuery("cid", companyId);
			li = executeQuery(jql.toString(), paramsToQuery);
			li.size();
		}catch (RepositoryException re) {
			throw re;
		}finally {
			finishTransaction();
		}
		
		return li;
	}

	@Override
	public void deleteCompanyTags(Long companyId) throws RepositoryException {
		List<CompanyTag> li = new ArrayList<CompanyTag>();
		try {
			li = getTagsByCompany(companyId);
			for (CompanyTag companyTag : li) {
				beginTransaction();
				CompanyTag tagDel = getByPrimaryKey(companyTag.getKey());
				delete(tagDel);
				commitTransaction();
			}
		}catch (RepositoryException re) {
			rollbackTransaction();
			throw re;
		}finally {
			finishTransaction();
		}
		
	}
}
