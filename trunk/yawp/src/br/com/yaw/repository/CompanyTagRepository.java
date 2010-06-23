package br.com.yaw.repository;

import java.util.List;

import br.com.yaw.entity.CompanyTag;
import br.com.yaw.exception.RepositoryException;

public interface CompanyTagRepository {
	public void addTagsToCompany(List<CompanyTag> lista) throws RepositoryException;

	public List<CompanyTag> getTagsByCompany(Long companyId) throws RepositoryException;

	public void deleteCompanyTags(Long companyId) throws RepositoryException;
}
