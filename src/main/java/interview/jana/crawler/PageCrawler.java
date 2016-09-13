package interview.jana.crawler;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import interview.jana.processors.TaskProcessor;

/**
 * Crawler that visits all the discoverable pages from the input url, It finds
 * the emailIds from the discovered pages.
 * 
 * @author Anusha
 *
 */
public class PageCrawler {

	private static final String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}+$";
	private static final Pattern p = Pattern.compile(emailPattern);
	private Cleaner cleaner;
	private Whitelist whiteList;
	private String currentURL;
	private TaskProcessor processor;

	public PageCrawler(String currentURL, TaskProcessor processor) {
		// this.cache = FindEmailIds.cache;
		this.whiteList = Whitelist.basic();
		this.cleaner = new Cleaner(whiteList);
		this.currentURL = currentURL;
		this.processor = processor;

	}

	/**
	 * Makes a http connection to the url and reads the connect as a document if
	 * Successful inserts the url in the page cache, finds the emailIds listed
	 * in the doc finds other links in the page submit those to be crawled
	 * 
	 * @param URL
	 */
	public void processPage() {
		if (!FindEmailIds.cache.isPageAvailable(currentURL)) {
			try {
				Document dirtydoc = Jsoup.connect(currentURL).get();
				Document doc = cleaner.clean(dirtydoc);
				FindEmailIds.cache.insertPage(currentURL);
				crawlinternalPages(doc);

			} catch (HttpStatusException e1) {
				System.err.println("Unable to connect to :" + e1.getUrl() + ":" + e1.getStatusCode());
			} catch (org.jsoup.UnsupportedMimeTypeException e2) {
				System.err.println("Unable to Parse Mime type:" + e2.getUrl() + ":" + e2.getMimeType());
			} catch (SocketTimeoutException e3) {
				System.err.println(e3.getMessage() + ":" + currentURL);
			} catch (IOException e) {
				System.err.println(e.getMessage() + ":" + currentURL);
			}
		}

	}

	public void crawlinternalPages(Document doc) {

		printEmailIds(getEmailIds(doc));
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String url = link.attr("abs:href");
			url = url.trim();
			// restricting based on levels
			if (FindEmailIds.level != Integer.MAX_VALUE) {
				if (canSkipUrl(url)) {
					continue;
				}
			}
			// restricting based external Urls
			if (url.contains(FindEmailIds.homeURL)) {
				if (!FindEmailIds.cache.isPageAvailable(url)) {
					processor.submitForProcessing(url);
				}
				// processPage(url);
			}
		}
	}

	private boolean canSkipUrl(String url) {
		StringTokenizer st = new StringTokenizer(url, "/");
		int count = st.countTokens();
		int allowed = FindEmailIds.level + 3;
		if (count > allowed)
			return false;
		else
			return true;
	}

	public void printEmailIds(Elements emails) {
		for (Element id : emails) {
			FindEmailIds.emailStore.addEmailIds(id.text());
		}
	}

	public Elements getEmailIds(Document doc) {
		Elements emails = doc.getElementsMatchingText(p);
		return emails;
	}

}
