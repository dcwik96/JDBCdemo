package com.example.jdbcdemo.service;

import java.util.List;

import com.example.jdbcdemo.domain.Book;

public interface BookManager {

	int addBook(Book book);
	void addAllBooks(List<Book> books);
	
	int deleteBookById(Long id);
	void deleteAllBooks();
	
	int updateWholeBook(Book book, String title, String author, double price);
	int updateBookTitle(Book book, String title);
	int updateBookAuthor(Book book, String author);
	int updateBookPrice(Book book, double price);
	
	List<Book> getAllBooks();
	List<Book> findByTitle(String title);
	List<Book> findByAuthor(String title);
	Book findById(long id);
	List<Book> findByPrice(double price);

	int deleteSelectedBooks(List<Long> ids);
	int updateSelectedBooks(List<Book> books);

	int ownMethod(List<Book> books);

	
}
