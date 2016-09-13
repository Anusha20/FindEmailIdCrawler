package interview.jana.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.validator.routines.UrlValidator;

import interview.jana.caches.EmailStoreCache;
import interview.jana.caches.InMemoryCache;
import interview.jana.caches.InMemoryEmailStore;
import interview.jana.caches.PageCache;
import interview.jana.caches.RedisCache;
import interview.jana.processors.JedisTaskProcessor;
import interview.jana.processors.RunnableProcessor;
import interview.jana.processors.TaskProcessor;
import interview.jana.redis.JedisManager;

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
	public static int level = Integer.MAX_VALUE;
	public static EmailStoreCache emailStore;

	public static void main(String args[]) {
		if (args.length == 2) {
			String inputlevel = args[1];
			if (isInteger(inputlevel)) {
				level = Integer.parseInt(inputlevel);
			}
		}
		String validUrl = validateUrl(args[0]);
		if (validUrl != null) {
			initialize();
			UseJedisPubsub(validUrl);
			System.out.println("Found Processing:" + validUrl);
			System.out.println("Found these emailIds:");
		} else {
			System.out.println("Invalid URL");
		}

	}

	private static boolean isInteger(String s) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), 10) < 0)
				return false;
		}
		return true;
	}

	private static String validateUrl(String input) {
		String validURL = null;
		if (input != null) {
			String url = "http://" + input;
			UrlValidator urlValidator = new UrlValidator();
			if (urlValidator.isValid(url)) {
				homeURL = input;
				validURL = url;
			}
		}
		return validURL;

	}

	// Initialize using Remote caches
	public static void initialize() {
		cache = new RedisCache();
		cache.clearCache();
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
			processor.submitForProcessing(url);
		} catch (InterruptedException e) {
			e.printStackTrace();
			shutdown(e.getMessage());
		}

	}

	public static void shutdown(String cause) {
		System.out.println("Shutting down : " + cause);
		executor.shutdownNow();
		cache.clearCache();
		emailStore.clear();
		JedisManager.shutdown();
		System.exit(1);

	}

	public static void initializeWithInMemoryCache(String url) {
		homeURL = url;
		cache = new InMemoryCache();
		emailStore = new InMemoryEmailStore();

	}
}
