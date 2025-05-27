package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
