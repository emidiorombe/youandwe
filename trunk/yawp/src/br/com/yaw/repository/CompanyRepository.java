package br.com.yaw.repository;

import java.util.List;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;
import br.com.yaw.exception.RepositoryException;

/**
 * Define os serviçõs de persistência de uma empresa.
 * @author Rafael Nunes
 *
 */
public interface CompanyRepository {
	
	Company getById(long id) throws RepositoryException;
	
}
