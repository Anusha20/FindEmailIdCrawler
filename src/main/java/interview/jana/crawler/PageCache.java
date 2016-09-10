package interview.jana.crawler;

public interface PageCache {
	
	public boolean isPageAvailable(String URL);
	
	public boolean insertPage(String URL);
	
	

}
