package com.example.redis.redissample.repo;

import com.example.redis.redissample.model.GeoLocation;
import com.example.redis.redissample.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class RedisItemRepository {
    public static final String HASH_KEY = "ITEM_HASH";
    public static final String SET_KEY = "ITEM_SET";
    public static final String ZSET_KEY = "ITEM_ZSET";
    public static final String LIST_KEY = "ITEM_LIST";
    public static final String GEO_KEY = "GEO_LOCATIONS";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;
    private SetOperations setOperations;
    private ListOperations listOperations;
    private ZSetOperations zSetOperations;
    private GeoOperations geoOperations;

    public RedisItemRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
        setOperations = redisTemplate.opsForSet();
        listOperations = redisTemplate.opsForList();
        zSetOperations = redisTemplate.opsForZSet();
        geoOperations = redisTemplate.opsForGeo();
        redisTemplate.opsForGeo();
    }

    /*Getting all Items from tSable*/
    public Map<String,Item> getAllItemFromHash(){
        return hashOperations.entries(HASH_KEY);
    }

    public Set<Item> getAllItemFromSets(){
        return setOperations.members(SET_KEY);
    }

    public List<Item> getAllItemFromLists() {
        return listOperations.range(LIST_KEY, 0, listOperations.size(LIST_KEY));
    }

    /*Getting a specific item by item id from table*/
    public Item getItem(String itemId){
        return (Item) hashOperations.get(HASH_KEY,itemId);
    }

    public Set<Item> getRandomItemsFromSet (long count) {
        return setOperations.distinctRandomMembers(SET_KEY, count);
    }

    /*Adding an item into redis database*/
    public void addItem(Item item){
        hashOperations.put(HASH_KEY,item.getId(),item);
        setOperations.add(SET_KEY, item);
        listOperations.leftPush(LIST_KEY, item);
        zSetOperations.add(ZSET_KEY, item, System.nanoTime());
    }

    /*Adding an item into redis database*/
    public void addGeoLocation(GeoLocation geo){
        geoOperations.add(GEO_KEY, new Point(geo.getLat(), geo.getLon()), geo.getCity());
    }

    public GeoResults getGeoRadius(String source, double value) {
        return geoOperations.radius(GEO_KEY, source, value);
    }

    public Distance getGeoDistance(String source, String target) {
        return geoOperations.distance(GEO_KEY, source, target);
    }

    /*Adding an item into redis database*/
    public void addItems(Item item, int count){
        for (int i=0; i < count; i++) {
            Item tempItem = new Item();
            tempItem.setId(item.getId()+i);
            tempItem.setName(item.getName() + i);
            tempItem.setCategory(item.getCategory()+i);
            addItem(tempItem);
        }
    }

    /*delete an item from database*/
    public void deleteItem(String id){
        hashOperations.delete(HASH_KEY,id);
    }

    /*update an item from database*/
    public void updateItem(Item item){
        addItem(item);
    }
}
