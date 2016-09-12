package interview.jana.caches;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEmailStore implements EmailStoreCache {
	private ConcurrentHashMap<String,Boolean> emailIds = new ConcurrentHashMap<String,Boolean>();
	/**
	 * Printing the Ids if they are newly added to the store
	 * @param id
	 */
	public void addEmailIds(String id){
		if(emailIds.putIfAbsent(id,true) == null)
		{
			System.out.println(id);
		}
	}
	/**
	 * Not used at present
	 */
	public void printAllIds(){
		System.out.println("Found these emailIds:");
		for(String ids:emailIds.keySet()){
			System.out.println(ids);
		}
	}
}
