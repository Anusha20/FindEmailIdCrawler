package interview.jana.crawler;

import interview.jana.processors.TaskProcessor;

public class MockProcessor implements TaskProcessor {

	String URL;
	PageCrawler crawler;
	TaskProcessor processor ;
	public void submitForProcessing(String URL) {
		 processor = new MockProcessor();
		crawler =  new PageCrawler(URL,processor);
		this.URL = URL;
		OnReceivingURL(URL);
	}

	
	public String getURL(){
		return URL;
	}

	public TaskProcessor getUsedProcessor(){
		
		return processor;
	}

	public void OnReceivingURL(String URL) {
		crawler.processPage();
		
	}
}
