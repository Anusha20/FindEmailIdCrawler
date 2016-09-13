package interview.jana.processors;

public interface TaskProcessor {
	/**
	 * Submitting a URL to a be crawled
	 * @param URL
	 */
	public void submitForProcessing(String URL);
	/**
	 * On receiving a URL to be crawled
	 */
	public void OnReceivingURL(String URL);
	
	
}
