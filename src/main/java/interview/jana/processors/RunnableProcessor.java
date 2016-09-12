package interview.jana.processors;

import interview.jana.crawler.FindEmailIds;
import interview.jana.crawler.PageCrawler;

public class RunnableProcessor implements TaskProcessor,Runnable {

	private PageCrawler crawler;
	
	public RunnableProcessor(){
		
	}
	public void submitForProcessing(String URL) {
		RunnableProcessor task = new RunnableProcessor();
		task.crawler = new PageCrawler(URL, task);
		FindEmailIds.executor.submit(task);
		
	}

	public void OnReceivingURL(String URL) {
		this.crawler.processPage();
		
	}

	public void run() {
		OnReceivingURL(null);
		
	}



}
