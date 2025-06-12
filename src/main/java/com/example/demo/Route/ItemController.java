package com.example.demo.Route;

import java.util.List;
import java.util.Optional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Items;
import com.example.demo.DTO.ItemDTO;
import com.example.demo.Repository.ItemRepository;

import jakarta.persistence.Entity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	private ItemRepository itemRepo;
	
	@GetMapping("/getitem")
	public ResponseEntity<?> getListItem(){
		List<Items> item = itemRepo.findAll();
		if(item.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Items Not Found");
		}
		return ResponseEntity.ok(item);
	}
	
	@PostMapping("/createitem")
	public ResponseEntity<?> createItem(@RequestBody ItemDTO item){
		Optional<Items> existProductName = itemRepo.findByProductName(item.getProductName());
		
		if(existProductName.isPresent()) {
			Items existingItems = existProductName.get();
			existingItems.setQuantity(existingItems.getQuantity() + item.getQuantity());
			existingItems.setPrice(item.getPrice());
			itemRepo.save(existingItems);
			return ResponseEntity.ok("Item Updated");
		}else {
			Items items = new Items();
			items.setProductName(item.getProductName());
			items.setQuantity(item.getQuantity());
			items.setPrice(item.getPrice());
			
			itemRepo.save(items);			
			return ResponseEntity.ok("Item Added");
		}
		
	}
	
	@PutMapping("updateitem/{id}")
	public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ItemDTO item) {
		Optional<Items> optItems = itemRepo.findById(id);		
		
		if(optItems.isPresent()) {
			Items existingItems = optItems.get();
			
			if(item.getProductName() != null) {				
				existingItems.setProductName(item.getProductName());
			}
			
			if(item.getQuantity() != null) {				
				existingItems.setQuantity(item.getQuantity());
			}
			
			itemRepo.save(existingItems);
			return ResponseEntity.ok("Item Updated");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item Not Found");
		}
	}
	
	@DeleteMapping("/deleteitem/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable Long id){
		Optional<Items> optItems = itemRepo.findById(id);
		if(optItems.isPresent()) {
			itemRepo.deleteById(id);
			return ResponseEntity.ok("Item Deleted");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item Not Found");
		}
	}
	
	@GetMapping("/getitem/{id}")
	public ResponseEntity<?> getItemById(@PathVariable Long id) {
		Optional<Items> optItems = itemRepo.findById(id);
		if(optItems.isPresent()) {
			return ResponseEntity.ok(optItems.get());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item Not Found");
		}
	}
}
