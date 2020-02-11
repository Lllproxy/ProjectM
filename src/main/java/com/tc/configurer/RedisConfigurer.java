package com.tc.configurer;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Redis配置
 * 
 * @author hfx
 *
 * @datetime 2018-8-30 上午11:46:51
 *
 * @version 1.0
 */
@Configuration
@EnableCaching
@PropertySource(value = "redis/redis-${spring.profiles.active}.properties")
public class RedisConfigurer  extends CachingConfigurerSupport  {
	
	@Value("${spring.redis.host}") String hostName;
    @Value("${spring.redis.port}") int port;
    @Value("${spring.redis.password}") String password;
    @Value("${spring.redis.testOnBorrow}") boolean testOnBorrow;
    @Value("${spring.redis.pool.max-idle}") int maxIdle;
    @Value("${spring.redis.pool.max-active}") int maxTotal;
    @Value("${spring.redis.database}") int index;
    @Value("${spring.redis.pool.max-wait}") long maxWaitMillis;
    
    @Value("${spring.redis-second.host}") String hostName2;
    @Value("${spring.redis-second.port}") int port2;
    @Value("${spring.redis-second.password}") String password2;
    @Value("${spring.redis-second.testOnBorrow}") boolean testOnBorrow2;
    @Value("${spring.redis-second.pool.max-idle}") int maxIdle2;
    @Value("${spring.redis-second.pool.max-active}") int maxTotal2;
    @Value("${spring.redis-second.database}") int index2;
    @Value("${spring.redis-second.pool.max-wait}") long maxWaitMillis2;
    
	@Bean
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};

	}

	@SuppressWarnings("rawtypes")
	@Bean
	public CacheManager cacheManager(@Qualifier(value="redisDefaultTemplate") RedisTemplate redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// Number of seconds before expiration. Defaults to unlimited (0)
		// cacheManager.setDefaultExpiration(10); // 设置key-value超时时间
		return cacheManager;
	}
	
	
    public RedisConnectionFactory connectionFactory(String hostName, int port, String password, int maxIdle,
            int maxTotal, int index, long maxWaitMillis, boolean testOnBorrow) {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(hostName);
        jedis.setPort(port);
        if (StringUtils.isNotEmpty(password)) {
            jedis.setPassword(password);
        }
        if (index != 0) {
            jedis.setDatabase(index);
        }
        jedis.setPoolConfig(poolCofig(maxIdle, maxTotal, maxWaitMillis, testOnBorrow));
        // 初始化连接pool
        jedis.afterPropertiesSet();
        RedisConnectionFactory factory = jedis;
        return factory;
    }

   
	public JedisPoolConfig poolCofig(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
		JedisPoolConfig poolCofig = new JedisPoolConfig();
		poolCofig.setMaxIdle(maxIdle);
		poolCofig.setMaxTotal(maxTotal);
		poolCofig.setMaxWaitMillis(maxWaitMillis);
		poolCofig.setTestOnBorrow(testOnBorrow);
		return poolCofig;
	}
	
	
    public JedisConnectionFactory redisSecondConnectionFactory() {
       return (JedisConnectionFactory)connectionFactory(hostName2, port2, password2, maxIdle2, maxTotal2, index2, maxWaitMillis2, testOnBorrow2);       
    }

	
    public JedisConnectionFactory redisDefaultConnectionFactory() { 
       return (JedisConnectionFactory)connectionFactory(hostName, port, password, maxIdle, maxTotal, index, maxWaitMillis, testOnBorrow);
       
    }

//	@Bean
//	RedisTemplate<String, Integer> intRedisTemplate(JedisConnectionFactory connectionFactory) {
//		RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<String, Integer>();
//		redisTemplate.setConnectionFactory(connectionFactory);
//		return redisTemplate;
//	}

	@Bean
	Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(ObjectMapper objectMapper) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}
 
	
	
	@Bean(name = "redisDefaultTemplate")
	RedisTemplate<String, Object> objRedisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		
		redisTemplate.setConnectionFactory(redisDefaultConnectionFactory());
		
//		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
//		
//		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//		redisTemplate.setKeySerializer(stringRedisSerializer);
//		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		
		redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
		
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		
		return redisTemplate;
	}

	@Bean(name = "redisSecondTemplate")
	RedisTemplate<String, Object> objSecondRedisTemplate( ) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisSecondConnectionFactory());
		
		redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
		
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(stringRedisSerializer);
		
		return redisTemplate;
	}
	 


//	@Bean
//	ValueOperations<String, Integer> intOperations(RedisTemplate<String, Integer> redisTemplate) {
//		return redisTemplate.opsForValue();
//	}
//
//	@Bean
//	ValueOperations<String, Object> objOperations(RedisTemplate<String, Object> redisTemplate) {
//		return redisTemplate.opsForValue();
//	}
//
//	@Bean
//	ValueOperations<String, String> strOperations(RedisTemplate<String, String> redisTemplate) {
//		return redisTemplate.opsForValue();
//	}

}
