package interview.jana.caches;
/**
 * cache for storing the visited URLs must implement this interface
 * @author Anusha
 *
 */
public interface PageCache {
	
	public boolean isPageAvailable(String URL);
	
	public boolean insertPage(String URL);
	
	public void clearCache();
	
	

}
