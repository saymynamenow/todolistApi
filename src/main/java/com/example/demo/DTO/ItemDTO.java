package com.example.demo.DTO;

public class ItemDTO {
	private String productName;
	private Integer quantity;
	private double price;
	
	public ItemDTO(String productName, Integer quantity, double price) {
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
