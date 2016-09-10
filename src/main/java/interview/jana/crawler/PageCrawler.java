package interview.jana.crawler;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.regex.Pattern;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class PageCrawler implements Runnable {

	private PageCache cache;
	private String homeURL;
	private static String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}+$";
	private Cleaner cleaner;
	private Whitelist whiteList;
	private String currentURL;
	private static EmailStore emailStore= new EmailStore();

	public PageCrawler(PageCache cache, String homeURL, String currentURL) {
		this.cache = cache;
		this.homeURL = homeURL;
		this.whiteList = Whitelist.basic();
		this.cleaner = new Cleaner(whiteList);
		this.currentURL = currentURL;
		
	}

	public void processPage(String URL) {
		if (!cache.isPageAvailable(URL)) {
			try {
				Document dirtydoc = Jsoup.connect(URL).get();
				Document doc = cleaner.clean(dirtydoc);
				cache.insertPage(URL);
				crawlinternalPages(doc);

			} catch (HttpStatusException e1) {
				//LOGGER.log(Level.FINER, "Unable to connect to " + e1.getUrl() + e1.getStatusCode());
				System.err.println("Unable to connect to " + e1.getUrl() + e1.getStatusCode());
			} catch (org.jsoup.UnsupportedMimeTypeException e2) {
				//LOGGER.log(Level.FINER, "Unable to Parse Mime type" + e2.getUrl() + e2.getMimeType());
				System.err.println("Unable to Parse Mime type" + e2.getUrl() + e2.getMimeType());
			} catch (SocketTimeoutException e3) {
				//LOGGER.log(Level.FINER, e3.getMessage());
				System.err.println(e3.getMessage());
			} catch (IOException e) {
				//LOGGER.log(Level.FINER, e.getMessage());
				System.err.println( e.getMessage());
			}
		}

	}

	public void crawlinternalPages(Document doc) {
		
		
		printEmailIds(getEmailIds(doc));
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String url = link.attr("abs:href");
			url = url.trim();
			//System.out.println(url);
			if (url.contains(homeURL)) {
				FindEmailIds.executor.submit(new PageCrawler(this.cache, this.homeURL, url));
				//processPage(url);
			}
		}
	}
	

	public void printEmailIds(Elements emails){
		for (Element id : emails) {
			emailStore.addEmailIds(id.text());
		}
	}
	public Elements getEmailIds(Document doc) {
		Pattern p = Pattern.compile(emailPattern);
		Elements emails = doc.getElementsMatchingText(p);
		return emails;
	}

	public void run() {
		processPage(currentURL);

	}

}
