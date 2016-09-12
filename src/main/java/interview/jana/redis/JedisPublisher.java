package interview.jana.redis;

import redis.clients.jedis.Jedis;

public class JedisPublisher implements Runnable {
	private String URL;

	public JedisPublisher(String URL) {
		this.URL = URL;

	}

	public void run() {
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedisConnection();
			jedis.publish("CrawlerQueue", URL);
		} catch (Exception e) {
			log(">>> OH NOES Pub, " + e.getMessage());
			// e.printStackTrace();

		} finally {
			jedis.close();
		}
	}

	static final long startMillis = System.currentTimeMillis();

	private static void log(String string, Object... args) {
		long millisSinceStart = System.currentTimeMillis() - startMillis;
		System.out.printf("%20s %6d %s\n", Thread.currentThread().getName(), millisSinceStart,
				String.format(string, args));
	}
}
