package interview.jana.crawler;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache implements PageCache {

	private ConcurrentHashMap<String,Boolean> pages = new ConcurrentHashMap<String,Boolean>();
	
	
	@Override
	public boolean isPageAvailable(String URL) {
		// TODO Auto-generated method stub
		return pages.containsKey(URL);
	}

	@Override
	public boolean insertPage(String URL) {
		if(pages.putIfAbsent(URL,true)==null)
			return true;
		return false;
	}
	
}
