package interview.jana.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
/**
 * Manages Jedis connection Pool
 * @author Anusha
 *
 */
public class JedisManager {

	public static JedisPool redisPool = new JedisPool(new JedisPoolConfig(), "pub-redis-18145.us-east-1-2.3.ec2.garantiadata.com", 18145,
			Protocol.DEFAULT_TIMEOUT, "123456");

	public JedisManager() {

	}

	public static Jedis getJedisConnection() {
		Jedis redis = null;
		try {
			redis = redisPool.getResource();
			return redis;
		} catch (JedisConnectionException e) {
			throw e;
		} 
	}
	
	public static void closeConnection(){
		redisPool.close();
	}

}
