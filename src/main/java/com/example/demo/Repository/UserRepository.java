package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.User;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);
}
