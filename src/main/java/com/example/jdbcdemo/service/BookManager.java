package com.example.jdbcdemo.service;

import java.util.List;

import com.example.jdbcdemo.domain.Book;

public interface BookManager {

	public int addBook(Book book);
	public void addAllBooks(List<Book> books);
	
	public int deleteBook(Book book);
	public void deleteAllBooks();
	
	public int updateWholeBook(Book book, String title, String author, double price);
	public int updateBookTitle(Book book, String title);
	public int updateBookAuthor(Book book, String author);
	public int updateBookPrice(Book book, double price);
	
	public List<Book> getAllBooks();
	public List<Book> findByTitle(String title);
	public List<Book> findByAuthor(String title);
	public Book findById(long id);
	public List<Book> findByPrice(double price);
	
}
