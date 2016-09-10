package interview.jana.crawler;

import java.util.concurrent.ConcurrentHashMap;

public class EmailStore {
	private ConcurrentHashMap<String,Boolean> emailIds = new ConcurrentHashMap<String,Boolean>();
	
	public void addEmailIds(String id){
		if(emailIds.putIfAbsent(id,true) == null)
		{
			System.out.println(id);
		}
	}
	public void printAllIds(){
		System.out.println("Found these emailIds:");
		for(String ids:emailIds.keySet()){
			System.out.println(ids);
		}
	}
}
