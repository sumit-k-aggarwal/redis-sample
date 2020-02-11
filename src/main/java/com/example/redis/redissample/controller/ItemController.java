package com.example.redis.redissample.controller;

import com.example.redis.redissample.model.Item;
import com.example.redis.redissample.repo.RedisItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class ItemController {
    @Autowired
    RedisItemRepository itemRepo;

    @RequestMapping("/getRandomItemsFromSet/{count}")
    @ResponseBody
    public ResponseEntity<List<Item>> getRandomItemsFromSet(@PathVariable int count){
        List items =  itemRepo.getRandomItemsFromSet(count);
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @RequestMapping("/getAllItemsFromSet")
    @ResponseBody
    public ResponseEntity<Set<Item>> getAllItemsFromSet(){
        Set items =  itemRepo.getAllItemFromSets();
        return new ResponseEntity<Set<Item>>(items, HttpStatus.OK);
    }

    @RequestMapping("/getAllItemsFromList")
    @ResponseBody
    public ResponseEntity<List<Item>> getAllItemsFromList(){
        List items =  itemRepo.getAllItemFromLists();
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @RequestMapping("/getAllItemsFromHash")
    @ResponseBody
    public ResponseEntity<Map<Integer, Item>> getAllItemsFromHash(){
        Map<Integer,Item> items =  itemRepo.getAllItemFromHash();
        return new ResponseEntity<Map<Integer,Item>>(items, HttpStatus.OK);
    }

    @GetMapping("/item/{itemId}")
    @ResponseBody
    public ResponseEntity<Item> getItem(@PathVariable int itemId){
        Item item = itemRepo.getItem(itemId);
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @PostMapping(value = "/addItem",consumes = {"application/json"},produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Item> addItem(@RequestBody Item item, UriComponentsBuilder builder){
        itemRepo.addItem(item);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/addItem/{id}").buildAndExpand(item.getId()).toUri());
        return new ResponseEntity<Item>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/addItems/{count}",consumes = {"application/json"},produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Item> addItems(@RequestBody Item item, @PathVariable int count, UriComponentsBuilder builder){
        itemRepo.addItems(item, count);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/addItem/{id}").buildAndExpand(item.getId()).toUri());
        return new ResponseEntity<Item>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/updateItem")
    @ResponseBody
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        if(item != null){
            itemRepo.updateItem(item);
        }
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteItem(@PathVariable int id){
        itemRepo.deleteItem(id);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
