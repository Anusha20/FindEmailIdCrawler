package interview.jana.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import interview.jana.caches.EmailStoreCache;
import interview.jana.caches.InMemoryCache;
import interview.jana.caches.InMemoryEmailStore;
import interview.jana.caches.PageCache;
import interview.jana.caches.RedisCache;
import interview.jana.processors.JedisTaskProcessor;
import interview.jana.processors.RunnableProcessor;
import interview.jana.processors.TaskProcessor;

/**
 * Main Class used to configure the caches and Executors
 * 
 * @author Anusha
 *
 */
// TODO Need to use proper Factories for initializing
public class FindEmailIds {

	public static ExecutorService executor = Executors.newSingleThreadExecutor();
	public static PageCache cache;
	public static String homeURL;
	public static EmailStoreCache emailStore;

	public static void main(String args[]) {

		initialize(args[0]);
		String url = "http://" + homeURL;
		System.out.println("Found Processing:" + url);
		UseJedisPubsub(url);
		System.out.println("Found these emailIds:");

	}

	// Initialize using Remote caches
	public static void initialize(String url) {
		homeURL = url;
		RedisCache.clearCache();
		cache = new RedisCache();
		emailStore = (EmailStoreCache) cache;
	}

	/**
	 * Uses Multithreading to process URL
	 * 
	 * @param url
	 */
	public static void UseLocalExecutor(String url) {
		TaskProcessor processor = new RunnableProcessor();
		processor.submitForProcessing(url);
	}

	/**
	 * Uses redis pub subs to distribute the URL processing across different
	 * processes
	 * 
	 * @param url
	 */
	public static void UseJedisPubsub(String url) {
		TaskProcessor processor = new JedisTaskProcessor();
		try {
			((JedisTaskProcessor) processor).startJedisSubScriber();
			// Need to make sure that the subscriber is initialized before the
			// publisher;
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		processor.submitForProcessing(url);

	}

	public static void initializeWithInMemoryCache(String url) {
		homeURL = url;
		cache = new InMemoryCache();
		emailStore = new InMemoryEmailStore();

	}
}
