package com.example.demo.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Items;
import com.example.demo.Status;
import com.example.demo.Transaction;
import com.example.demo.TransactionItem;
import com.example.demo.User;
import com.example.demo.DTO.ItemDTO;
import com.example.demo.DTO.ItemResponseDTO;
import com.example.demo.DTO.ItemTransactionDTO;
import com.example.demo.DTO.StatusRequestDTO;
import com.example.demo.DTO.TransactionDTO;
import com.example.demo.DTO.TransactionResponseDTO;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.TransactionRepository;


@RestController
@RequestMapping("/transactions")
public class TransactionController {


	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ItemRepository itemRepo;

	
	@RequestMapping("/gettransaction")
	public ResponseEntity<?> getTransactionList() {
	    List<Transaction> transactions = transactionRepo.findAll();

	    if (transactions.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions");
	    }

	    List<TransactionResponseDTO> dtoList = transactions.stream().map(tx -> {
	    	List<ItemDTO> itemDTOs = tx.getItems().stream().map(item -> 
	        new ItemDTO(item.getItem().getProductName(), item.getQuantity(), item.getItem().getPrice())
	    ).collect(Collectors.toList());

	        return new TransactionResponseDTO(tx.getId(), tx.getUser().getId(), itemDTOs, tx.getGrandTotal(), tx.getStatus(), tx.getCreatedAt());
	    }).collect(Collectors.toList());


	    return ResponseEntity.ok(dtoList);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getTransactionsByUser(@PathVariable Long userId) {
	    List<Transaction> transactions = transactionRepo.findByUserId(userId);	

	    if (transactions.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions found for user ID: " + userId);
	    }

	    List<TransactionResponseDTO> dtoList = transactions.stream().map(tx -> {
	    	List<ItemDTO> itemDTOs = tx.getItems().stream().map(item -> 
	        new ItemDTO(item.getItem().getProductName(), item.getQuantity(), item.getItem().getPrice())
	    ).collect(Collectors.toList());

	        return new TransactionResponseDTO(tx.getId(), tx.getUser().getId(), itemDTOs, tx.getGrandTotal(), tx.getStatus(), tx.getCreatedAt());
	    }).collect(Collectors.toList());


	    return ResponseEntity.ok(dtoList);
	}
	
	@PostMapping("/createtransaction")
	public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transactionDTO) {
		User user = userRepo.findById(transactionDTO.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
		
		Transaction transaction = new Transaction();
		transaction.setUser(user);
		transaction.setGrandTotal(transactionDTO.getGrandTotal());
		transaction.setStatus(transactionDTO.getStatus());
		transaction.setCreatedAt(transactionDTO.getCreatedAt());
		
		List<TransactionItem> transactionItems = new ArrayList<>();
		
		for(ItemTransactionDTO itemDTO : transactionDTO.getItems()) {
			Items product = itemRepo.findById(itemDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product Not Found"));
			TransactionItem transactionItem = new TransactionItem();
			
			if(product.getQuantity() < itemDTO.getQuantity()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Quantity of Product");
			}
			
			product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
			itemRepo.save(product);
			
			transactionItem.setItem(product);
			transactionItem.setQuantity(itemDTO.getQuantity());
			transactionItem.setTransaction(transaction);
			transactionItems.add(transactionItem);
		}
		
		
		transaction.setItems(transactionItems);
		
		Transaction savedTransaction = transactionRepo.save(transaction);
		return ResponseEntity.status(HttpStatus.CREATED).body("User Transaction Created Successfully with ID: " + savedTransaction.getId());
	}
	
	@RequestMapping("/deletetransaction/{id}")
	public ResponseEntity<?> deleteTransaction(Long id) {
		if(transactionRepo.existsById(id)) {
			transactionRepo.deleteById(id);
			return ResponseEntity.ok("Transaction Deleted Successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction Not Found");
		}
	}
	
	@PostMapping("/changestatus/{id}")
	public ResponseEntity<?> changeTransactionStatus(@PathVariable Long id, @RequestBody StatusRequestDTO status) {
		Transaction transaction = transactionRepo.findById(id).orElseThrow(() -> new RuntimeException("Transaction Not Found"));
		if(status.getStatus() == Status.CANCELLED) {
			List<TransactionItem> items = transaction.getItems();
			for(TransactionItem item : items) {
				Items product = item.getItem();
				product.setQuantity(product.getQuantity() + item.getQuantity());
				itemRepo.save(product);
			}
		}
		transaction.setStatus(status.getStatus());
		transactionRepo.save(transaction);
		
		return ResponseEntity.ok("Transaction Status Updated Successfully");
	}
}
