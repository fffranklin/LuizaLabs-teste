package com.pedidoprocessor.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private int orderId;
	private String date;
	private List<Product> products = new ArrayList<>();
	private double total;
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	public void addToTotal(double value) {
		this.total += value;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}
