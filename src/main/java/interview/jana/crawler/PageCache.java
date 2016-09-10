package interview.jana.crawler;
/**
 * cache for storing the visited URLs must implement this interface
 * @author Anusha
 *
 */
public interface PageCache {
	
	public boolean isPageAvailable(String URL);
	
	public boolean insertPage(String URL);
	
	

}
