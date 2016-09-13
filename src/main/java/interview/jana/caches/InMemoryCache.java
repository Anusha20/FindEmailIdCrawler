package interview.jana.caches;

import java.util.concurrent.ConcurrentHashMap;
/**
 * In Memory cache currently used to store the visted pages
 * @author Anusha
 *
 */
public class InMemoryCache implements PageCache {

	private ConcurrentHashMap<String,Boolean> pages = new ConcurrentHashMap<String,Boolean>();
		
	public boolean isPageAvailable(String URL) {
		return pages.containsKey(URL);
	}

	public boolean insertPage(String URL) {
		if(pages.putIfAbsent(URL,true)==null)
			return true;
		return false;
	}

	public void clearCache() {
		pages.clear();
		
	}
	
}
