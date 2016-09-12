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
				FindEmailIds.initializeWithInMemoryCache("abc.com");
				Elements ele = new PageCrawler("http://abc.com",new MockProcessor()).getEmailIds(doc);
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
				FindEmailIds.initializeWithInMemoryCache("abc.com");
				MockProcessor processor = new MockProcessor();
				 PageCrawler crawler =  new PageCrawler("abc.com",processor);
				 crawler.crawlinternalPages(doc);
				org.junit.Assert.assertEquals(true,FindEmailIds.cache.isPageAvailable("http://example1.abc.com") );
				org.junit.Assert.assertEquals(false,FindEmailIds.cache.isPageAvailable("http://example2.com") );
	}

}
