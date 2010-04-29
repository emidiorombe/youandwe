package br.com.yaw.service;

import java.util.List;

import br.com.yaw.entity.Company;
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
}
