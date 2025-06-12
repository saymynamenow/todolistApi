package com.example.demo.DTO;

import java.sql.Date;
import java.util.List;

import com.example.demo.Status;

public class TransactionResponseDTO {

    private Long userId;
    private Long id;
    private List<ItemDTO> items;
    private int grandTotal;
    private Status status;
    private Date createdAt;

    public TransactionResponseDTO(Long id, Long userId, List<ItemDTO> items, int grandTotal, Status status, Date createdAt) {
        this.id = id;
    	this.userId = userId;
        this.items = items;
        this.grandTotal = grandTotal;
        this.status = status;
        this.createdAt = createdAt;
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

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }


	public int getGrandTotal() {
		return grandTotal;
	}


	public void setGrandTotal(int grandTotal) {
		this.grandTotal = grandTotal;
	}
    
    
}
