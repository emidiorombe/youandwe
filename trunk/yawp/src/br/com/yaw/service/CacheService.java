package br.com.yaw.service;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Deque;

import br.com.yaw.entity.Comment;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;


/**
 * Serviços de cache
 * @author Rafael Nunes
 *
 */
public class CacheService {
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
		Deque<Comment> q = (Deque<Comment>) generalCache.get("comments");
		if( q == null){
			q = new LinkedList<Comment>();
		}
		
		q.add(c);
		generalCache.put("comments", q);
	}
	
	public static Collection<Comment> getLatestComments(){
		return (Collection<Comment>) generalCache.get("comments");
	}

	public static void addComment(Collection<Comment> latestComments) {
		Deque<Comment> q = (Deque<Comment>) generalCache.get("comments");
		if( q == null){
			q = new LinkedList<Comment>();
		}
		q.addAll(latestComments);
		generalCache.put("comments", q);
	}
	
	
}
