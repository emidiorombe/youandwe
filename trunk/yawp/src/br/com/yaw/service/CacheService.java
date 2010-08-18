package br.com.yaw.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.yaw.entity.Comment;
import br.com.yaw.entity.Company;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;


/**
 * Serviços de cache
 * @author Rafael Nunes
 *
 */
public class CacheService {
	private enum CACHE_COLLECTIONS {COMMENTS, COMPANIES}
	
	private static Cache generalCache;
	
	static{
		try{
			generalCache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
		} catch (CacheException ce) {
			System.err.println("::::::Não foi possível criar o cache de comentários => " + ce.getMessage());
			ce.printStackTrace();
		}finally{
			
		}
	}
	
	public static void addComment(Comment c){
		List<Comment> q = (List<Comment>) generalCache.get(CACHE_COLLECTIONS.COMMENTS.toString());
		if( q == null){
			q = new LinkedList<Comment>();
		}
		
		q.add(c);
		generalCache.put(CACHE_COLLECTIONS.COMMENTS.toString(), q);
	}
	
	public static Collection<Comment> getLatestComments(){
		return (Collection<Comment>) generalCache.get(CACHE_COLLECTIONS.COMMENTS.toString());
	}

	public static void addComment(Collection<Comment> latestComments) {
		List<Comment> q = (List<Comment>) generalCache.get(CACHE_COLLECTIONS.COMMENTS.toString());
		if( q == null){
			q = new LinkedList<Comment>();
		}
		q.addAll(latestComments);
		generalCache.put(CACHE_COLLECTIONS.COMMENTS.toString(), q);
	}
	
	public static Map<String, Company> getCompanies() {
		return (Map<String, Company>) generalCache.get(CACHE_COLLECTIONS.COMPANIES.toString());
	}
	
	public static void addCompany(Company company) {
		Map<String, Company> map = (Map<String, Company>) generalCache.get(CACHE_COLLECTIONS.COMPANIES.toString()); 
		if(map == null) {
			map = new HashMap<String, Company>();
		}
		map.put(Long.toString(company.getKey().getId()), company);
		generalCache.put(CACHE_COLLECTIONS.COMPANIES.toString(), map);
	}
	
	public static void addCompanies(Collection<Company> companies) {
		for (Company company : companies) {
			addCompany(company);
		}
	}

	public static void removeComment(Comment c) {
		generalCache.put(CACHE_COLLECTIONS.COMMENTS.toString(), null);
		
	}
	
}
