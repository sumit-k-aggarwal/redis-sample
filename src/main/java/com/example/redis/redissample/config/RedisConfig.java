package com.example.redis.redissample.config;

import com.example.redis.redissample.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Autowired
    ClusterConfigurationProperties clusterProperties;

    public @Bean
    RedisConnectionFactory connectionFactory() {

        return new JedisConnectionFactory(
                new RedisClusterConfiguration(clusterProperties.getNodes()));
    }

    /*@Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisProperties properties = redisProperties();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(properties.getHost());
        configuration.setPort(properties.getPort());

        return new JedisConnectionFactory(configuration);
    }*/

    @Bean
    public RedisTemplate<String, Item> redisTemplate() {
        final RedisTemplate<String, Item> template = new RedisTemplate<String, Item>();
        template.setConnectionFactory(connectionFactory());
        // template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

    @Bean
    @Primary
    public RedisProperties redisProperties() {
        return new RedisProperties();
    }
}
