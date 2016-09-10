package interview.jana.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FindEmailIds {
	
	public static ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public static void main(String args[]){
		
		
        String homeURL  = args[0];
        String url = "http://"+homeURL;
        System.out.println("Found Processing:"+ url);
		PageCache cache = new InMemoryCache();
		PageCrawler crawler = new PageCrawler(cache,homeURL,url );
		System.out.println("Found these emailIds:");
		executor.submit(crawler);
		
	}

}
