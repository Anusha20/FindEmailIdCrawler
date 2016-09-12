package interview.jana.caches;

import interview.jana.redis.JedisManager;
import redis.clients.jedis.Jedis;

/**
 * Cache storing the visited page URL's at the Remote redis
 * 
 * @author Anusha
 *
 */
public class RedisCache implements PageCache, EmailStoreCache {

	public boolean isPageAvailable(String URL) {
		Jedis jedis = null;
		boolean result = false;
		try {
			jedis = JedisManager.getJedisConnection();
			result = jedis.hexists(URL, "URL");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
		return result;
	}

	public boolean insertPage(String URL) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedisConnection();
			Long val = jedis.hset(URL, "URL", "URL");
			if (val == 1) {
				result = true;
			}
		} catch (Exception e) {

		} finally {
			jedis.close();
		}
		return result;

	}

	public static void clearCache() {
		JedisManager.getJedisConnection().flushAll();
	}

	public void addEmailIds(String id) {
		Jedis jedis = null;
		try {
			jedis = JedisManager.getJedisConnection();
			Long val = jedis.hset(id, "EmailId", "EmailId");
			if (val == 1) {
				System.out.println(id);
			}
		} catch (Exception e) {

		} finally {
			jedis.close();
		}

	}
}
