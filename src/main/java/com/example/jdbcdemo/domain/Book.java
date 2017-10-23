package com.example.jdbcdemo.domain;

public class Book {

	private long id;
	
	private String title;
	private String author;
	private double price;
	
	public Book(){
	}
	
	public Book(String title, String author, double price){
		super();
		this.title = title;
		this.author = author;
		this.price = price;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
