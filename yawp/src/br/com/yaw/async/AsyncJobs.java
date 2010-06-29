package br.com.yaw.async;

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
	
}
