package com.example.demo.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	public List<Transaction> findByUserId(Long userId);
	

}
