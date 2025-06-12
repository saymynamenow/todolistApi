package com.example.demo.Route;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.Repository.UserRepository;

import jakarta.persistence.PrePersist;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
    private UserRepository userRepo;


	@PostMapping("/registration")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		Boolean userExist = userRepo.existsByUsername(user.getUsername());
		if(userExist) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username sudah di pakai");
		}else {
			User userData = userRepo.save(user);
			return ResponseEntity.ok(userData);
		}

	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO logDTO){
		Optional<User> user = userRepo.findByUsername(logDTO.getUsername());
		if(user.isPresent()) {
			if(user.get().getPassword().equals(logDTO.getPassword())) {
				return ResponseEntity.ok(user.get());
			}
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password Wrong");

		}else {			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username not found");
		}
	}
	
	@GetMapping("/getprofile/{id}")
	public ResponseEntity<?> getProfile(@PathVariable Long id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
	}
	
	@GetMapping("/getuser")
	public ResponseEntity<?> getUserList() {
		var users = userRepo.findAll();
		if(users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
		}
		return ResponseEntity.ok(users);
	}
}