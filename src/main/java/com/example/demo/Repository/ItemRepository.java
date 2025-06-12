package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Items;

public interface ItemRepository extends JpaRepository<Items, Long>{
	Optional<Items> findByProductName(String productName);
}
