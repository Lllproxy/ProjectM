package com.tc.dao.redis;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisOptUtil {
    

	 
	/**
	 * 在redis数据key前加前缀，防止不同项目共用redis，key重复
	 * @param key
	 * @return
	 */	
	public static String getKey(String prefix,String key){
	 	return prefix+"_"+key;
	}
	 
	/**
     * @param key
     */
    public static long del(RedisTemplate<Serializable, Serializable> redisTemplate,final String... keys) {
        
        
    	return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });
    }
 
    /**
     * @param key
     * @param value
     * @param liveTime 单位秒
     */
    public static void set(RedisTemplate<Serializable, Serializable> redisTemplate,final byte[] key, final byte[] value, final long liveTime) {
    	
    	
    	redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }
 
    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public static void set(RedisTemplate<Serializable, Serializable> redisTemplate,String key, String value, long liveTime) {
    	
    	
    	set(redisTemplate,key.getBytes(), value.getBytes(), liveTime);
    }
    /**
     * 
     */
    public static void set(RedisTemplate<Serializable, Serializable> redisTemplate,Serializable key, Serializable value){
    	
    	
    	redisTemplate.opsForValue().set(key, value);
    }
    /**
     * @param key
     * @param value
     */
    public static void set(RedisTemplate<Serializable, Serializable> redisTemplate,String key, String value) {
    	
    	
    	set(redisTemplate,key, value, 0L);
    }
 
    /**
     * @param key
     * @param value
     */
    public static void set(RedisTemplate<Serializable, Serializable> redisTemplate,byte[] key, byte[] value) {
    	
    	
    	set(redisTemplate,key, value, 0L);
    }
 
    /**
     * @param key
     * @return
     */
    public static String get(RedisTemplate<Serializable, Serializable> redisTemplate,final String key,final String redisCode) {
    	
    	if (redisTemplate == null)
    		return null;
    	
    	return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                	byte[] bk = connection.get(key.getBytes());
                	if (bk != null && bk.length>0)
                		return new String(bk, redisCode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
    }
    
    public static byte[] get(RedisTemplate<Serializable, Serializable> redisTemplate,final byte[] key) {
    	 
    	
    	return redisTemplate.execute(new RedisCallback<byte[]>() {

			public byte[] doInRedis(RedisConnection connection)
					throws DataAccessException {

				return connection.get(key);

			}

		});

	}
 
    public static Serializable get(RedisTemplate<Serializable, Serializable> redisTemplate,final Serializable key){
    	
    	
    	return redisTemplate.opsForValue().get(key);
    }
    
    /**
     * @param pattern
     * @return
     */
    public static Set<byte[]>  keys(RedisTemplate<Serializable, Serializable> redisTemplate,final String pattern) {
    	
    	if (redisTemplate == null)
    		return null;
    	
    	return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException { 
                	return connection.keys(pattern.getBytes()); 
            }
        });
    }
 
    /**
     * @param key
     * @return
     */
    public static boolean exists(RedisTemplate<Serializable, Serializable> redisTemplate,final String key) {
    	
    	
    	return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }
 
    /**
     * @return
     */
    public static String flushDB(RedisTemplate<Serializable, Serializable> redisTemplate) {
    	
    	
    	return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }
 
    /**
     * @return
     */
    public static long dbSize(RedisTemplate<Serializable, Serializable> redisTemplate) {
    	
    	
    	return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }
 
    /**
     * @return
     */
    public static String ping(RedisTemplate<Serializable, Serializable> redisTemplate) {
    	
    	
    	return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
 
                return connection.ping();
            }
        });
    }
    
     
}
