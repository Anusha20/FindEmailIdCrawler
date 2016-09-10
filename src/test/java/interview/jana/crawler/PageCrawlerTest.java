package interview.jana.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class PageCrawlerTest {

	@Test
	public void testGetEmailIds() {
		String html = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p>"
				  + "<li>xbu.abc.com</li>"
				  + "<li>xbu@abc.com</li>"
				  +"</body></html>";
				Document doc = Jsoup.parse(html);
				Elements ele = new PageCrawler(new InMemoryCache(), "abc.com", "http://abc.com").getEmailIds(doc);
				org.junit.Assert.assertEquals(1, ele.size());
				org.junit.Assert.assertEquals("xbu@abc.com", ele.get(0).text());
	}
	//@Test
	public void testcrawlInternalPages() {
		String html = "<html><head><title>First parse</title></head>"
				  + "<body><p>Parsed HTML into a doc.</p>"
				  + "<li>xbu.abc.com</li>"
				  + "<li>xbu@abc.com</li>"
				  +"<a href=\"http://example1.abc.com\"><b>example1</b></a>"
				  +"<a href=\"http://example2.com\"><b>example2</b></a>"
				  +"</body></html>";
				Document doc = Jsoup.parse(html);
				PageCache cache = new InMemoryCache();
				new PageCrawler(cache, "abc.com", "http://abc.com").crawlinternalPages(doc);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				org.junit.Assert.assertEquals(true,cache.isPageAvailable("http://example1.abc.com") );
				org.junit.Assert.assertEquals(false,cache.isPageAvailable("http://example2.com") );
	}

}
