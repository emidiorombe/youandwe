package br.com.yaw.async;

import br.com.yaw.entity.Company;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Builder;

/**
 * Classe responsável por registrar os processamentos asincronos.
 * @author Rafael Nunes
 *
 */
public class AsyncJobs {
	
	public static void rebuildCompassIndex() {
		Queue queue = QueueFactory.getQueue("compass-index");
		queue.add(Builder.url("/async/compass_rebuild"));
	}
	
	public static void splitImage(String imgKey, long userId){
		Queue queue = QueueFactory.getQueue("image-splitter");
		queue.add(Builder.url("/image/split").param("img", imgKey).param("uid", Long.toString(userId)));
	}
	
	public static void addCompanyToCache(Company c) {
		Queue queue = QueueFactory.getQueue("cache-handle");
		queue.add(Builder.url("/async/cache_company/"+c.getKey().getId()));
	}
	
	public static void addAllCompaniesToCache() {
		Queue queue = QueueFactory.getQueue("cache-handle");
		queue.add(Builder.url("/async/cache_companies/"));
	}
	
}
