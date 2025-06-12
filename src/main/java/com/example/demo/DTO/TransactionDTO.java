package com.example.demo.DTO;

import java.sql.Date;
import java.util.List;

import com.example.demo.Status;

public class TransactionDTO {

	private Long userId;
	private List<ItemTransactionDTO> items;
	private int grandTotal;

	private Status status;
	
	private Date createdAt;
	
	public TransactionDTO(Long userId, List<ItemTransactionDTO> items, int grandTotal) {
		super();
		this.userId = userId;
		this.items = items;
		this.grandTotal = grandTotal;
		this.status = Status.PENDING;
		this.createdAt = new Date(System.currentTimeMillis());
	}
	
	
	
	
	public Date getCreatedAt() {
		return createdAt;
	}




	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}




	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<ItemTransactionDTO> getItems() {
		return items;
	}
	public void setItems(List<ItemTransactionDTO> items) {
		this.items = items;
	}
	
	
	public int getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(int grandTotal) {
		this.grandTotal = grandTotal;
	}
}

