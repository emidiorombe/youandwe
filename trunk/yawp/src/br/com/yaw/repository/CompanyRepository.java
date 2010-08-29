package br.com.yaw.repository;

import java.util.Collection;
import java.util.List;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;
import br.com.yaw.entity.CompanyTag;
import br.com.yaw.exception.RepositoryException;

/**
 * Define os serviçõs de persistência de uma empresa.
 * @author Rafael Nunes
 *
 */
public interface CompanyRepository {
	
	Company getById(long id) throws RepositoryException;
	
	void addCompany(Company company) throws RepositoryException;

	List<Company> getAllCompanies() throws RepositoryException;

	void removeCompany(long companyId)throws RepositoryException;

	Collection<Company> getByName(String query) throws RepositoryException;

	Collection<Company> getByCity(String string)throws RepositoryException;

	Collection<Company> getByTag(String tags)throws RepositoryException;

	Collection<Company> getByBairro(String bairro) throws RepositoryException;
	
}
