package br.com.yaw.service;

import java.util.List;
import java.util.Map;

import br.com.yaw.entity.Company;
import br.com.yaw.entity.CompanyTag;
import br.com.yaw.exception.ServiceException;

public interface CompanyService {
	/**
	 * Busca uma empresa baseado em seu id.
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	Company getCompanyById(long id) throws ServiceException; 
	
	void addCompany(Company c) throws ServiceException;

	List<Company> getAllCompanies() throws ServiceException;

	void addTags(String parameter, Long companyId) throws ServiceException;

	List<CompanyTag> getCompanyTags(long id) throws ServiceException;

	void remove(long companyId) throws ServiceException;

	List<Company> findCompanies(Map<String, String> query)throws ServiceException;
}
