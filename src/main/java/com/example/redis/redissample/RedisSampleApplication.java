package com.example.redis.redissample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSampleApplication.class, args);
	}

	/*@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		return factory;
	}

	@Bean
	RedisTemplate<String, Item> redisTemplate(){
		RedisTemplate<String,Item> redisTemplate = new RedisTemplate<String, Item>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}*/
}
