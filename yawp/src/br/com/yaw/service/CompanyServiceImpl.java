package br.com.yaw.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import br.com.yaw.async.AsyncJobs;
import br.com.yaw.entity.Company;
import br.com.yaw.entity.CompanyTag;
import br.com.yaw.exception.RepositoryException;
import br.com.yaw.exception.ServiceException;
import br.com.yaw.ioc.ServiceFactory;
import br.com.yaw.repository.CompanyRepository;
import br.com.yaw.repository.CompanyTagRepository;
import br.com.yaw.utils.StringUtilities;

public class CompanyServiceImpl implements CompanyService{
	private CompanyRepository companyRepository;
	private CompanyTagRepository tagRepository;
	
	
	@Override
	public Company getCompanyById(long id) throws ServiceException {
		Company c = null;
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			c = companyRepository.getById(id);
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.empresa", re);
		}
		return c;
	}
	
	public void addCompany(Company c) throws ServiceException{
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			companyRepository.addCompany(c);
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.inserir.empresa", re);
		}
	}

	@Override
	public List<Company> getAllCompanies() throws ServiceException {
		List<Company> lista = new ArrayList<Company>();
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			lista = companyRepository.getAllCompanies();
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.empresa", re);
		}
		return lista;
	}

	@Override
	public void addTags(String parameter, Long companyId) throws ServiceException {
		String tokens[] = parameter != null ?  parameter.split(",") : new String[0];
		try {
			List<CompanyTag> lista = new ArrayList<CompanyTag>();
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			tagRepository = ServiceFactory.getService(CompanyTagRepository.class);
			tagRepository.deleteCompanyTags(companyId);
			
			for (String tag : tokens) {
				if(!tag.equals(",") && !tag.equals(" ") && !tag.equals(""))
					lista.add(new CompanyTag(tag.trim(), companyId));
			}
			tagRepository.addTagsToCompany(lista);
		}catch (RepositoryException re) {
			re.printStackTrace();
			throw new ServiceException(re);
		}
		
	}

	@Override
	public List<CompanyTag> getCompanyTags(long companyId) throws ServiceException {
		List<CompanyTag> lista = new ArrayList<CompanyTag>();
		try {
			tagRepository = ServiceFactory.getService(CompanyTagRepository.class);
			lista = tagRepository.getTagsByCompany(companyId);
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.buscar.empresa", re);
		}
		return lista;
	}

	@Override
	public void remove(long companyId) throws ServiceException {
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			companyRepository.removeCompany(companyId);
		} catch (RepositoryException re) {
			throw new ServiceException("impossivel.remover.empresa", re);
		}
		
	}
	
	
	@Override
	public List<Company> searchCompanyByFields(Map<String, String> params) throws ServiceException {
		HashSet<Company> set = new HashSet<Company>();
		
		set.addAll(searchCompanyByName(params.get("nome_c")));
		set.addAll(searchCompanyByCity(params.get("cidade_c")));
		set.addAll(searchCompanyByBairro(params.get("bairro_c")));
		set.addAll(searchCompanyByTag(params.get("categoria_c")));
		
		
		return new ArrayList<Company>(set);
	}
	
	@Override
	public List<Company> searchCompanyByName(String name) throws ServiceException {
		List<Company> lista = new ArrayList<Company>();
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			lista.addAll(companyRepository.getByName(StringUtilities.getSearchableString(name).toLowerCase()));
		}catch(RepositoryException re) {
			throw new ServiceException(re);
		}
		return lista;
	}
	
	@Override
	public List<Company> searchCompanyByCity(String city) throws ServiceException {
		List<Company> lista = new ArrayList<Company>();
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			lista.addAll(companyRepository.getByCity(StringUtilities.getSearchableString(city).toLowerCase()));
		}catch(RepositoryException re) {
			throw new ServiceException(re);
		}
		return lista;
	}
	
	@Override
	public List<Company> searchCompanyByBairro(String bairro) throws ServiceException {
		List<Company> lista = new ArrayList<Company>();
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			lista.addAll(companyRepository.getByBairro(StringUtilities.getSearchableString(bairro).toLowerCase()));
		}catch(RepositoryException re) {
			throw new ServiceException(re);
		}
		return lista;
	}
	
	@Override
	public List<Company> searchCompanyByTag(String tag) throws ServiceException {
		List<Company> lista = new ArrayList<Company>();
		try {
			companyRepository = ServiceFactory.getService(CompanyRepository.class);
			lista.addAll(companyRepository.getByTag(StringUtilities.getSearchableString(tag).toLowerCase()));
		}catch(RepositoryException re) {
			throw new ServiceException(re);
		}
		return lista;
	}
	
	@Override
	public List<Company> findCompanies(Map<String, String> query) throws ServiceException {
		
		HashSet<Company> set = new HashSet<Company>();
		set.addAll(searchCompanyByName(query.get("categoria_ou_nome")));
		set.addAll(searchCompanyByTag(query.get("categoria_ou_nome")));
		set.addAll(searchCompanyByCity(query.get("local")));		
		
		
		return new ArrayList<Company>(set);
	}

}
