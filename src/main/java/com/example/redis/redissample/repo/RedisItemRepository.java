package com.example.redis.redissample.repo;

import com.example.redis.redissample.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RedisItemRepository {
    public static final String KEY = "ITEM";
    @Autowired
    private RedisTemplate<String, Item> redisTemplate;
    private HashOperations hashOperations;

    public RedisItemRepository(RedisTemplate<String, Item> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    /*Getting all Items from tSable*/
    public Map<Integer,Item> getAllItems(){
        return hashOperations.entries(KEY);
    }

    /*Getting a specific item by item id from table*/
    public Item getItem(int itemId){
        return (Item) hashOperations.get(KEY,itemId);
    }

    /*Adding an item into redis database*/
    public void addItem(Item item){
        hashOperations.put(KEY,item.getId(),item);
    }

    /*delete an item from database*/
    public void deleteItem(int id){
        hashOperations.delete(KEY,id);
    }

    /*update an item from database*/
    public void updateItem(Item item){
        addItem(item);
    }
}
