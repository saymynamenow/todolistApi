package com.example.demo.Route;
import com.example.demo.DTO.PostDTO;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.PostRepository;
import com.example.demo.Repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.example.demo.Category;
import com.example.demo.Post;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/post")
public class PostController {



	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CategoryRepository categoryRepository;


	
	
	@GetMapping("/getPost")
	public ResponseEntity<?> getPost() {
		return ResponseEntity.ok(postRepository.findAll());
	}
	
	@PostMapping("/createPost")
	public ResponseEntity<?> createPost(@RequestBody PostDTO data) {
		Category category = categoryRepository.findById(data.getCategoryId()).orElseThrow(() -> new RuntimeException("Category Not Found"));
		
		Post post = new Post();
	    post.setTitle(data.getTitle());
	    post.setDescription(data.getDescription());
	    post.setCheck(data.isCheck());
	    post.setDeadLine(data.getDeadLine());
	    post.setCategoryId(category);
	    
	    Post posted = postRepository.save(post);
	    
	    if(posted != null) {	    	
	    	return ResponseEntity.ok("Post Berhasil Di simpan");
	    }
    	return ResponseEntity.ok("Fail");
	}
	
	
	@DeleteMapping("/deletePost/{id}")
	public ResponseEntity<?> deltePost(@PathVariable Long id) {
		if(!postRepository.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data Not Found");
		}
		postRepository.deleteById(id);
		
		return ResponseEntity.ok("Data Deleted");
	}
	
	
	
	
}
