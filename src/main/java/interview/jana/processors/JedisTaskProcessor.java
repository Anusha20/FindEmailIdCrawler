package interview.jana.processors;

import interview.jana.redis.JedisManager;
import interview.jana.redis.JedisPublisher;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisTaskProcessor implements TaskProcessor {

	/**
	 * Sending the URL to the publisher to be published 
	 */
	public void submitForProcessing(String URL) {
		new Thread(new JedisPublisher(URL), "publisherThread").start();
	}
	/***
	 * Submitting the URL to be processed
	 */
	public void OnReceivingURL(String URL) {
		new RunnableProcessor().submitForProcessing(URL);
	}
	/**
	 * Starting Jedis Subscriber
	 * @throws InterruptedException
	 */
	public void startJedisSubScriber() throws InterruptedException {
		final JedisPubSub jedisPubSub = new JedisListener();
		new Thread(new Runnable() {
			public void run() {
				Jedis jedis = null;
				try {
					jedis = JedisManager.getJedisConnection();
					jedis.subscribe(jedisPubSub, "CrawlerQueue");

				} catch (Exception e) {
					log(">>> OH NOES Sub - " + e.getMessage());
				} finally {
					jedis.close();
				}
			}
		}, "subscriberThread").start();

	}

	static final long startMillis = System.currentTimeMillis();

	private static void log(String string, Object... args) {
		long millisSinceStart = System.currentTimeMillis() - startMillis;
		System.out.printf("%20s %6d %s\n", Thread.currentThread().getName(), millisSinceStart,
				String.format(string, args));

	}
	/**
	 * Receives the URL to be processed
	 * @author Anusha
	 *
	 */
	class JedisListener extends JedisPubSub {
		@Override
		public void onUnsubscribe(String channel, int subscribedChannels) {
			log("onUnsubscribe");
		}

		@Override
		public void onSubscribe(String channel, int subscribedChannels) {
		}

		@Override
		public void onPUnsubscribe(String pattern, int subscribedChannels) {
		}

		@Override
		public void onPSubscribe(String pattern, int subscribedChannels) {
		}

		@Override
		public void onPMessage(String pattern, String channel, String message) {
		}

		@Override
		public void onMessage(String channel, String message) {
			OnReceivingURL(message);
		}
	}
}
